package tivi;

import java.io.Serializable;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Tivi implements Serializable {
    private String hangSanXuat;
    private int kichCoManHinh;

    public Tivi() {
        this.hangSanXuat = "Chưa xác định";
        this.kichCoManHinh = 0;
    }

    public Tivi(String hangSanXuat, int kichCoManHinh) {
        this.hangSanXuat = hangSanXuat;
        this.kichCoManHinh = kichCoManHinh;
    }

    public String getHangSanXuat() {
        return hangSanXuat;
    }

    public void setHangSanXuat(String hangSanXuat) {
        this.hangSanXuat = hangSanXuat;
    }

    public int getKichCoManHinh() {
        return kichCoManHinh;
    }

    public void setKichCoManHinh(int kichCoManHinh) {
        this.kichCoManHinh = kichCoManHinh;
    }

    public void nhapTivi() throws InputMismatchException, IllegalArgumentException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập hãng sản xuất: ");
        hangSanXuat = scanner.nextLine();
        do {
            try {
                System.out.print("Nhập kích cỡ màn hình (inch): ");
                kichCoManHinh = scanner.nextInt();
                if (kichCoManHinh <= 0) {
                    throw new IllegalArgumentException("Kích cỡ màn hình phải lớn hơn 0!");
                }
                break;
            } catch (InputMismatchException e) {
                throw new InputMismatchException("Kích cỡ màn hình phải là số nguyên!");
            }
        } while (true);
    }

    public void xuatTivi() {
        System.out.println("Hãng sản xuất: " + hangSanXuat);
        System.out.println("Kích cỡ màn hình: " + kichCoManHinh + " inch");
    }

    @Override
    public String toString() {
        return hangSanXuat + "," + kichCoManHinh + "inch";
    }
}