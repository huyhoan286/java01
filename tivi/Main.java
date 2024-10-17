package tivi;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Tivi> danhSachTivi = new ArrayList<>();
        int luaChon;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Thêm tivi");
            System.out.println("2. Hiển thị danh sách tivi");
            System.out.println("3. Xóa tivi");
            System.out.println("4. Tìm kiếm tivi");
            System.out.println("5. Lưu danh sách vào tệp văn bản");
            System.out.println("6. Tải danh sách từ tệp văn bản");
            System.out.println("7. Lưu danh sách vào tệp nhị phân");
            System.out.println("8. Tải danh sách từ tệp nhị phân");
            System.out.println("9. Thoát");
            System.out.print("Lựa chọn: ");
            luaChon = scanner.nextInt();

            switch (luaChon) {
                case 1:
                    themTivi(scanner, danhSachTivi);
                    break;
                case 2:
                    hienThiDanhSachTivi(danhSachTivi);
                    break;
                case 3:
                    xoaTivi(scanner, danhSachTivi);
                    break;
                case 4:
                    timKiemTivi(scanner, danhSachTivi);
                    break;
                case 5:
                    luuDanhSachVaoFile(scanner, danhSachTivi);
                    break;
                case 6:
                    danhSachTivi = taiDanhSachTuFile(scanner);
                    break;
                case 7:
                    luuDanhSachVaoFileNhiPhan(scanner,danhSachTivi);
                    break;
                case 8:
                    danhSachTivi = taiDanhSachTuFileNhiPhan();
                    break;
                case 9:
                    System.out.println("Kết thúc chương trình!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!");
            }
        } while (luaChon != 9);
    }

    private static void themTivi(Scanner scanner, List<Tivi> danhSachTivi) {
        System.out.println("Chọn loại tivi:");
        System.out.println("1. SmartTivi");
        System.out.println("2. Tivi3D");
        int luaChon = scanner.nextInt();

        boolean nhapThanhCong = false;

        do {
            try {
                if (luaChon == 1) {
                    SmartTivi smartTivi = new SmartTivi();
                    smartTivi.nhapTivi();
                    danhSachTivi.add(smartTivi);
                    nhapThanhCong = true;
                } else if (luaChon == 2) {
                    Tivi3D tivi3D = new Tivi3D();
                    tivi3D.nhapTivi();
                    danhSachTivi.add(tivi3D);
                    nhapThanhCong = true;
                } else {
                    System.out.println("Lựa chọn không hợp lệ.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Vui lòng nhập lại.");
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage() + " Vui lòng nhập lại.");
                scanner.nextLine();
            }
        } while (!nhapThanhCong);
    }

    private static void hienThiDanhSachTivi(List<Tivi> danhSachTivi) {
        if (danhSachTivi.isEmpty()) {
            System.out.println("Danh sách tivi trống.");
        } else {
            System.out.println("\nThông tin các tivi đã nhập:");
            for (int i = 0; i < danhSachTivi.size(); i++) {
                Tivi tivi = danhSachTivi.get(i);
                System.out.println("Tivi thứ " + (i + 1) + ":");
                if (tivi instanceof SmartTivi) {
                    ((SmartTivi) tivi).xuatTivi();
                } else if (tivi instanceof Tivi3D) {
                    ((Tivi3D) tivi).xuatTivi();
                }
                System.out.println("---------------------------");
            }
        }
    }

    private static void xoaTivi(Scanner scanner, List<Tivi> danhSachTivi) {
        if (danhSachTivi.isEmpty()) {
            System.out.println("Danh sách tivi trống.");
            return;
        }

        System.out.print("Nhập vị trí tivi muốn xóa (bắt đầu từ 1): ");
        int viTri = scanner.nextInt();
        if (viTri > 0 && viTri <= danhSachTivi.size()) {
            danhSachTivi.remove(viTri - 1);
            System.out.println("Đã xóa tivi ở vị trí " + viTri);
        } else {
            System.out.println("Vị trí không hợp lệ.");
        }
    }

    private static void timKiemTivi(Scanner scanner, List<Tivi> danhSachTivi) {
        if (danhSachTivi.isEmpty()) {
            System.out.println("Danh sách tivi trống.");
            return;
        }

        scanner.nextLine();
        System.out.println("Tìm kiếm theo:");
        System.out.println("1. Hãng sản xuất");
        System.out.println("2. Kích cỡ màn hình");
        int luaChon = scanner.nextInt();
        scanner.nextLine();

        boolean timThay = false;
        if (luaChon == 1) {
            System.out.print("Nhập hãng sản xuất muốn tìm: ");
            String hangSanXuat = scanner.nextLine();
            for (Tivi tivi : danhSachTivi) {
                if (tivi.getHangSanXuat().equalsIgnoreCase(hangSanXuat)) {
                    tivi.xuatTivi();
                    timThay = true;
                    System.out.println("---------------------------");
                }
            }
        } else if (luaChon == 2) {
            System.out.print("Nhập kích cỡ màn hình muốn tìm: ");
            int kichCo = scanner.nextInt();
            for (Tivi tivi : danhSachTivi) {
                if (tivi.getKichCoManHinh() == kichCo) {
                    tivi.xuatTivi();
                    timThay = true;
                    System.out.println("---------------------------");
                }
            }
        } else {
            System.out.println("Lựa chọn không hợp lệ.");
        }

        if (!timThay) {
            System.out.println("Không tìm thấy tivi phù hợp.");
        }
    }

    private static void luuDanhSachVaoFile(Scanner scanner, List<Tivi> danhSachTivi) {
        String filename = "danhsach.txt"; // Tên tệp sẽ được lưu
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Tivi tivi : danhSachTivi) {
                writer.println(tivi.toString());
            }
            System.out.println("Đã lưu danh sách tivi vào tệp " + filename);
            // Hiển thị thông tin đã lưu
            System.out.println("Thông tin tivi đã lưu:");
            hienThiDanhSachTivi(danhSachTivi);

            // Mở tệp bằng Notepad
            Runtime.getRuntime().exec("notepad.exe " + filename);
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu vào tệp: " + e.getMessage());
        }
    }

    private static List<Tivi> taiDanhSachTuFile(Scanner scanner) {
        List<Tivi> danhSachTivi = new ArrayList<>();
        String filename = "danhsach.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Kiểm tra xem dòng có đủ thông tin không
                if (parts.length < 6) {
                    System.out.println("Dữ liệu không hợp lệ trong tệp: " + line);
                    continue;  // Bỏ qua dòng này và đọc dòng tiếp theo
                }

                String loaiTivi = parts[0];
                String hangSanXuat = parts[1];
                int kichCoManHinh = Integer.parseInt(parts[2].replace("inch", "").trim());

                if (loaiTivi.equals("SmartTivi")) {
                    String heDieuHanh = parts[3];
                    double chieuDai = Double.parseDouble(parts[4]);
                    double chieuRong = Double.parseDouble(parts[5]);
                    SmartTivi smartTivi = new SmartTivi(hangSanXuat, kichCoManHinh, heDieuHanh);
                    smartTivi.setChieuDai(chieuDai);
                    smartTivi.setChieuRong(chieuRong);
                    danhSachTivi.add(smartTivi);
                } else if (loaiTivi.equals("Tivi3D")) {
                    int doPhanGiai3D = Integer.parseInt(parts[3]);
                    double chieuDai = Double.parseDouble(parts[4]);
                    double chieuRong = Double.parseDouble(parts[5]);
                    Tivi3D tivi3D = new Tivi3D(hangSanXuat, kichCoManHinh, doPhanGiai3D, chieuDai, chieuRong);
                    danhSachTivi.add(tivi3D);
                }
            }
            System.out.println("Đã tải danh sách tivi từ tệp " + filename);
        } catch (IOException e) {
            System.out.println("Lỗi khi tải từ tệp: " + e.getMessage());
        }
        return danhSachTivi;
    }

    private static void luuDanhSachVaoFileNhiPhan(Scanner scanner, List<Tivi> danhSachTivi) {
        String filename = "danhsach.dat"; // Tên tệp nhị phân
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(danhSachTivi);
            System.out.println("Đã lưu danh sách tivi vào tệp nhị phân " + filename);
            Runtime.getRuntime().exec("notepad.exe " + filename); // Mở tệp nhị phân bằng Notepad
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu vào tệp nhị phân: " + e.getMessage());
        }
    }

    private static List<Tivi> taiDanhSachTuFileNhiPhan() {
        List<Tivi> danhSachTivi = new ArrayList<>();
        String filename = "danhsach.dat"; // Tên tệp nhị phân sẽ được tải
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            danhSachTivi = (List<Tivi>) ois.readObject();
            System.out.println("Đã tải danh sách tivi từ tệp nhị phân " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Lỗi khi tải từ tệp nhị phân: " + e.getMessage());
        }
        return danhSachTivi;
    }

}