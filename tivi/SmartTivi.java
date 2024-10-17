package tivi;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.Serializable;

public class SmartTivi extends Tivi implements Serializable{
    private String heDieuHanh;
    private double chieuDai;
    private double chieuRong;

    public SmartTivi() {
        super();
        this.heDieuHanh = "Chưa xác định";
    }

    public SmartTivi(String hangSanXuat, int kichCoManHinh, String heDieuHanh) {
        super(hangSanXuat, kichCoManHinh);
        this.heDieuHanh = heDieuHanh;
    }

    public String getHeDieuHanh() {
        return heDieuHanh;
    }

    public void setHeDieuHanh(String heDieuHanh) {
        this.heDieuHanh = heDieuHanh;
    }

    public double getChieuDai() {
        return chieuDai;
    }

    public void setChieuDai(double chieuDai) {
        this.chieuDai = chieuDai;
    }

    public double getChieuRong() {
        return chieuRong;
    }

    public void setChieuRong(double chieuRong) {
        this.chieuRong = chieuRong;
    }

    public void nhapTivi() throws IllegalArgumentException, InputMismatchException {
        super.nhapTivi();
        Scanner scanner = new Scanner(System.in);
        boolean hopLe = false;

        do {
            try {
                System.out.print("Nhập chiều dài màn hình (cm): ");
                chieuDai = scanner.nextDouble();
                System.out.print("Nhập chiều rộng màn hình (cm): ");
                chieuRong = scanner.nextDouble();
                scanner.nextLine();

                if (chieuDai <= 0 || chieuRong <= 0 || chieuDai <= chieuRong) {
                    throw new IllegalArgumentException("Chiều dài phải lớn hơn chiều rộng và là số thực dương!");
                }
                hopLe = true;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Lỗi: Chiều dài và chiều rộng phải là số thực!");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!hopLe);

        System.out.print("Nhập hệ điều hành: ");
        heDieuHanh = scanner.nextLine();
    }

    public void xuatTivi() {
        super.xuatTivi();
        System.out.println("Chiều dài màn hình: " + chieuDai + " cm");
        System.out.println("Chiều rộng màn hình: " + chieuRong + " cm");
        System.out.println("Hệ điều hành: " + heDieuHanh);
    }

    @Override
    public String toString() {
        return "SmartTivi," + super.toString() + "," + heDieuHanh + "," + chieuDai + "," + chieuRong;
    }

}