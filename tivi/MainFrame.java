package tivi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private ArrayList<Tivi> danhSachTivi = new ArrayList<>();
    private DefaultTableModel tableModel;

    public MainFrame() {
        setTitle("Quản lý Tivi");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Các thành phần giao diện
        JLabel lbMaTivi = new JLabel("Mã Tivi:");
        lbMaTivi.setBounds(20, 20, 100, 30);
        add(lbMaTivi);

        JTextField tfMaTivi = new JTextField();
        tfMaTivi.setBounds(120, 20, 150, 30);
        add(tfMaTivi);

        JLabel lbTenTivi = new JLabel("Tên Tivi:");
        lbTenTivi.setBounds(20, 60, 100, 30);
        add(lbTenTivi);

        JTextField tfTenTivi = new JTextField();
        tfTenTivi.setBounds(120, 60, 150, 30);
        add(tfTenTivi);

        JLabel lbKichThuoc = new JLabel("Kích thước:");
        lbKichThuoc.setBounds(20, 100, 100, 30);
        add(lbKichThuoc);

        JTextField tfKichThuoc = new JTextField();
        tfKichThuoc.setBounds(120, 100, 150, 30);
        add(tfKichThuoc);

        JLabel lbGiaBan = new JLabel("Giá bán:");
        lbGiaBan.setBounds(20, 140, 100, 30);
        add(lbGiaBan);

        JTextField tfGiaBan = new JTextField();
        tfGiaBan.setBounds(120, 140, 150, 30);
        add(tfGiaBan);

        JLabel lbLoaiTivi = new JLabel("Loại Tivi:");
        lbLoaiTivi.setBounds(20, 180, 100, 30);
        add(lbLoaiTivi);

        String[] loaiTivi = {"SmartTivi", "Tivi3D"};
        JComboBox<String> cbLoaiTivi = new JComboBox<>(loaiTivi);
        cbLoaiTivi.setBounds(120, 180, 150, 30);
        add(cbLoaiTivi);

        // Thuộc tính riêng cho SmartTivi và Tivi3D
        JLabel lbHeDieuHanh = new JLabel("Hệ điều hành:");
        lbHeDieuHanh.setBounds(20, 220, 100, 30);
        add(lbHeDieuHanh);

        JTextField tfHeDieuHanh = new JTextField();
        tfHeDieuHanh.setBounds(120, 220, 150, 30);
        add(tfHeDieuHanh);

        JLabel lbDoPhanGiai3D = new JLabel("Độ phân giải 3D:");
        lbDoPhanGiai3D.setBounds(20, 220, 100, 30);
        lbDoPhanGiai3D.setVisible(false);
        add(lbDoPhanGiai3D);

        JTextField tfDoPhanGiai3D = new JTextField();
        tfDoPhanGiai3D.setBounds(120, 220, 150, 30);
        tfDoPhanGiai3D.setVisible(false);
        add(tfDoPhanGiai3D);

        // Hiển thị thuộc tính phù hợp khi chọn loại Tivi
        cbLoaiTivi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) cbLoaiTivi.getSelectedItem();
                if (selected.equals("SmartTivi")) {
                    lbHeDieuHanh.setVisible(true);
                    tfHeDieuHanh.setVisible(true);
                    lbDoPhanGiai3D.setVisible(false);
                    tfDoPhanGiai3D.setVisible(false);
                } else if (selected.equals("Tivi3D")) {
                    lbHeDieuHanh.setVisible(false);
                    tfHeDieuHanh.setVisible(false);
                    lbDoPhanGiai3D.setVisible(true);
                    tfDoPhanGiai3D.setVisible(true);
                }
            }
        });

        // Bảng hiển thị danh sách tivi
        String[] columnNames = {"Mã Tivi", "Tên Tivi", "Kích thước", "Giá bán", "Hệ điều hành", "Độ phân giải 3D"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 260, 550, 150);
        add(scrollPane);

        // Nút thêm
        JButton btnAdd = new JButton("Thêm");
        btnAdd.setBounds(300, 20, 100, 30);
        add(btnAdd);

        // Xử lý sự kiện nút thêm
        btnAdd.addActionListener(new ThemTiviListener(table, tfMaTivi, tfTenTivi, tfKichThuoc, tfGiaBan, cbLoaiTivi, tfHeDieuHanh, tfDoPhanGiai3D));

        // Nút xóa
        JButton btnDelete = new JButton("Xóa");
        btnDelete.setBounds(300, 60, 100, 30);
        add(btnDelete);

        // Xử lý sự kiện nút xóa
        btnDelete.addActionListener(new XoaTiviListener(table));

        // Nút tìm kiếm
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBounds(300, 100, 100, 30);
        add(btnSearch);

        // Xử lý sự kiện tìm kiếm
        btnSearch.addActionListener(new TimKiemTiviListener(table, tfMaTivi));

        // Nút lưu file nhị phân
        JButton btnSave = new JButton("Lưu NP");
        btnSave.setBounds(300, 140, 100, 30);
        add(btnSave);

        // Xử lý sự kiện lưu file nhị phân
        btnSave.addActionListener(new LuuTiviListener());

        // Nút mở file nhị phân
        JButton btnOpen = new JButton("Mở NP");
        btnOpen.setBounds(300, 180, 100, 30);
        add(btnOpen);

        // Xử lý sự kiện mở file nhị phân
        btnOpen.addActionListener(new MoTiviListener());

        // Nút lưu file văn bản
        JButton btnSaveText = new JButton("Lưu VB");
        btnSaveText.setBounds(420, 140, 150, 30);
        add(btnSaveText);

        // Xử lý sự kiện lưu file văn bản
        btnSaveText.addActionListener(new LuuTiviTextListener());

        // Nút mở file văn bản
        JButton btnOpenText = new JButton("Mở VB");
        btnOpenText.setBounds(420, 180, 150, 30);
        add(btnOpenText);

        // Xử lý sự kiện mở file văn bản
        btnOpenText.addActionListener(new MoTiviTextListener());

        // Nút tải từ CSDL


        // Nút sửa
        JButton btnEdit = new JButton("Sửa");
        btnEdit.setBounds(300, 220, 100, 30);
        add(btnEdit);

        // Xử lý sự kiện sửa
        btnEdit.addActionListener(new SuaTiviListener(table, tfMaTivi, tfTenTivi, tfKichThuoc, tfGiaBan, cbLoaiTivi, tfHeDieuHanh, tfDoPhanGiai3D));


        setVisible(true);
    }

    // Sự kiện thêm Tivi
    private class ThemTiviListener implements ActionListener {
        private final JTable table;
        private final JTextField tfMaTivi, tfTenTivi, tfKichThuoc, tfGiaBan, tfHeDieuHanh, tfDoPhanGiai3D;
        private final JComboBox<String> cbLoaiTivi;

        public ThemTiviListener(JTable table, JTextField tfMaTivi, JTextField tfTenTivi, JTextField tfKichThuoc,
                                JTextField tfGiaBan, JComboBox<String> cbLoaiTivi, JTextField tfHeDieuHanh,
                                JTextField tfDoPhanGiai3D) {
            this.table = table;
            this.tfMaTivi = tfMaTivi;
            this.tfTenTivi = tfTenTivi;
            this.tfKichThuoc = tfKichThuoc;
            this.tfGiaBan = tfGiaBan;
            this.cbLoaiTivi = cbLoaiTivi;
            this.tfHeDieuHanh = tfHeDieuHanh;
            this.tfDoPhanGiai3D = tfDoPhanGiai3D;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String maTivi = tfMaTivi.getText();
            String tenTivi = tfTenTivi.getText();
            int kichThuoc = Integer.parseInt(tfKichThuoc.getText());
            double giaBan = Double.parseDouble(tfGiaBan.getText());
            String loaiTivi = (String) cbLoaiTivi.getSelectedItem();

            Object[] rowData;
            if ("SmartTivi".equals(loaiTivi)) {
                String heDieuHanh = tfHeDieuHanh.getText();
                rowData = new Object[]{maTivi, tenTivi, kichThuoc, giaBan, heDieuHanh, ""}; // Không có độ phân giải 3D
            } else {
                int doPhanGiai3D = Integer.parseInt(tfDoPhanGiai3D.getText());
                rowData = new Object[]{maTivi, tenTivi, kichThuoc, giaBan, "", doPhanGiai3D}; // Không có hệ điều hành
            }

            tableModel.addRow(rowData);
//            JOptionPane.showMessageDialog(MainFrame.this, "Đã thêm Tivi!");

            // Xóa thông tin trên các trường nhập liệu
            tfMaTivi.setText("");
            tfTenTivi.setText("");
            tfKichThuoc.setText("");
            tfGiaBan.setText("");
            tfHeDieuHanh.setText("");
            tfDoPhanGiai3D.setText("");
        }
    }

    // Sự kiện xóa Tivi
    private class XoaTiviListener implements ActionListener {
        private final JTable table;

        public XoaTiviListener(JTable table) {
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                tableModel.removeRow(selectedRow);
//                JOptionPane.showMessageDialog(MainFrame.this, "Đã xóa Tivi!");
            } else {
                JOptionPane.showMessageDialog(MainFrame.this, "Vui lòng chọn Tivi để xóa!");
            }
        }
    }

    // Sự kiện tìm kiếm Tivi
    private class TimKiemTiviListener implements ActionListener {
        private final JTable table;

        public TimKiemTiviListener(JTable table, JTextField tfMaTivi) {
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Hiển thị hộp thoại yêu cầu nhập tên Tivi
            String tenTivi = JOptionPane.showInputDialog(MainFrame.this, "Nhập tên Tivi để tìm kiếm:");

            if (tenTivi != null && !tenTivi.trim().isEmpty()) {
                boolean found = false;

                // Tìm kiếm tên Tivi trong bảng
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 1).toString().equalsIgnoreCase(tenTivi.trim())) {
                        table.setRowSelectionInterval(i, i);
                        found = true;
                        break;
                    }
                }

                // Hiển thị kết quả
                if (found) {
//                    JOptionPane.showMessageDialog(MainFrame.this, "Tivi đã được tìm thấy!");
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Không tìm thấy Tivi với tên " + tenTivi);
                }
            } else {
                // Nếu không nhập hoặc nhập trống
                JOptionPane.showMessageDialog(MainFrame.this, "Vui lòng nhập tên Tivi hợp lệ.");
            }
        }
    }



    // Sự kiện lưu Tivi vào file
    private class LuuTiviListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("danhsach_tivi.dat"))) {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Object[] row = new Object[tableModel.getColumnCount()];
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        row[j] = tableModel.getValueAt(i, j);
                    }
                    oos.writeObject(row);
                }
                JOptionPane.showMessageDialog(MainFrame.this, "Đã lưu danh sách Tivi vào file!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Lỗi khi lưu danh sách Tivi: " + ex.getMessage());
            }
        }
    }

    // Sự kiện mở danh sách Tivi từ file
    private class MoTiviListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("danhsach_tivi.dat"))) {
                tableModel.setRowCount(0); // Xóa bảng trước khi thêm dữ liệu mới
                while (true) {
                    Object[] row = (Object[]) ois.readObject();
                    tableModel.addRow(row);
                }
            } catch (EOFException ex) {
                // Đã kết thúc tệp
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Lỗi khi mở danh sách Tivi: " + ex.getMessage());
            }
        }
    }
    //Sự kiện lưu Tivi vào file văn bản
    private class LuuTiviTextListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("danhsach_tivi.txt"))) {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        bw.write(tableModel.getValueAt(i, j).toString() + "\t");
                    }
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(MainFrame.this, "Đã lưu danh sách Tivi vào file văn bản!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Lỗi khi lưu danh sách Tivi: " + ex.getMessage());
            }
        }
    }

    // Sự kiện mở danh sách Tivi từ file văn bản
    private class MoTiviTextListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (BufferedReader br = new BufferedReader(new FileReader("danhsach_tivi.txt"))) {
                tableModel.setRowCount(0);
                String line;
                while ((line = br.readLine()) != null) {
                    String[] row = line.split("\t");
                    tableModel.addRow(row);
                }
//                JOptionPane.showMessageDialog(MainFrame.this, "Đã mở danh sách Tivi từ file văn bản!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Lỗi khi mở danh sách Tivi: " + ex.getMessage());
            }
        }
    }
    private class TaiFromDBListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Kết nối đến CSDL
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tivi_db", "root", "password");
                String query = "SELECT * FROM tivi";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                tableModel.setRowCount(0); // Xóa bảng trước khi tải dữ liệu mới
                while (rs.next()) {
                    String maTivi = rs.getString("maTivi");
                    String tenTivi = rs.getString("tenTivi");
                    int kichThuoc = rs.getInt("kichThuoc");
                    double giaBan = rs.getDouble("giaBan");
                    String heDieuHanh = rs.getString("heDieuHanh");
                    String doPhanGiai3D = rs.getString("doPhanGiai3D");
                    Object[] rowData = {maTivi, tenTivi, kichThuoc, giaBan, heDieuHanh, doPhanGiai3D};
                    tableModel.addRow(rowData);
                }

                conn.close();
                JOptionPane.showMessageDialog(MainFrame.this, "Dữ liệu đã được tải từ CSDL!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Lỗi khi tải dữ liệu từ CSDL: " + ex.getMessage());
            }
        }
    }

    private class SuaTiviListener implements ActionListener {
        private final JTable table;
        private final JTextField tfMaTivi, tfTenTivi, tfKichThuoc, tfGiaBan, tfHeDieuHanh, tfDoPhanGiai3D;
        private final JComboBox<String> cbLoaiTivi;

        public SuaTiviListener(JTable table, JTextField tfMaTivi, JTextField tfTenTivi, JTextField tfKichThuoc,
                               JTextField tfGiaBan, JComboBox<String> cbLoaiTivi, JTextField tfHeDieuHanh,
                               JTextField tfDoPhanGiai3D) {
            this.table = table;
            this.tfMaTivi = tfMaTivi;
            this.tfTenTivi = tfTenTivi;
            this.tfKichThuoc = tfKichThuoc;
            this.tfGiaBan = tfGiaBan;
            this.cbLoaiTivi = cbLoaiTivi;
            this.tfHeDieuHanh = tfHeDieuHanh;
            this.tfDoPhanGiai3D = tfDoPhanGiai3D;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String loaiTivi = (String) cbLoaiTivi.getSelectedItem();
                Object[] rowData;
                if ("SmartTivi".equals(loaiTivi)) {
                    rowData = new Object[]{tfMaTivi.getText(), tfTenTivi.getText(), Integer.parseInt(tfKichThuoc.getText()), Double.parseDouble(tfGiaBan.getText()), tfHeDieuHanh.getText(), ""};
                } else {
                    rowData = new Object[]{tfMaTivi.getText(), tfTenTivi.getText(), Integer.parseInt(tfKichThuoc.getText()), Double.parseDouble(tfGiaBan.getText()), "", tfDoPhanGiai3D.getText()};
                }

                // Cập nhật dòng đã chọn
                for (int i = 0; i < rowData.length; i++) {
                    tableModel.setValueAt(rowData[i], selectedRow, i);
                }

                // Xóa thông tin trên các trường nhập liệu sau khi sửa
                tfMaTivi.setText("");
                tfTenTivi.setText("");
                tfKichThuoc.setText("");
                tfGiaBan.setText("");
                tfHeDieuHanh.setText("");
                tfDoPhanGiai3D.setText("");
            } else {
                JOptionPane.showMessageDialog(MainFrame.this, "Vui lòng chọn một Tivi để sửa!");
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}