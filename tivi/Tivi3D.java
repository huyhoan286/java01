package tivi;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.Serializable;

public class Tivi3D extends Tivi implements Serializable{
    private int doPhanGiai3D;
    private double chieuDai;
    private double chieuRong;

   
    public Tivi3D() {
        super();
        this.doPhanGiai3D = 0;
    }

    public Tivi3D(String hangSanXuat, int kichCoManHinh, int doPhanGiai3D, double chieuDai, double chieuRong) {
        super(hangSanXuat, kichCoManHinh);
        this.doPhanGiai3D = doPhanGiai3D;
        this.chieuDai = chieuDai;
        this.chieuRong = chieuRong;
    }

  
    public int getDoPhanGiai3D() {
        return doPhanGiai3D;
    }

    public void setDoPhanGiai3D(int doPhanGiai3D) {
        this.doPhanGiai3D = doPhanGiai3D;
    }

   
    public void nhapTivi() {
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

                if (chieuDai > 0 && chieuRong > 0 && chieuDai > chieuRong) {
                    hopLe = true; 
                } else {
                    System.out.println("Lỗi:Chiều dài phải lớn hơn chiều rộng và là số thực dương!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Lỗi:Chiều dài phải lớn hơn chiều rộng và là số thực!");
                scanner.nextLine();
            }
        } while (!hopLe); 

        
        do {
            try {
                System.out.print("Nhập độ phân giải 3D: ");
                doPhanGiai3D = scanner.nextInt();
                if (doPhanGiai3D <= 0) {
                    System.out.println("Độ phân giải 3D phải là số nguyên dương! Vui lòng nhập lại.");
                } else {
                    break; 
                }
            } catch (InputMismatchException e) {
                System.out.println("Độ phân giải 3D phải là số nguyên! Vui lòng nhập lại.");
                scanner.nextLine(); 
            }
        } while (true);
    }

  
    public void xuatTivi() {
        super.xuatTivi(); 
        System.out.println("Chiều dài màn hình: " + chieuDai + " cm");
        System.out.println("Chiều rộng màn hình: " + chieuRong + " cm");
        System.out.println("Độ phân giải 3D: " + doPhanGiai3D);
    }
    
    @Override
    public String toString() {
        return "Tivi3D," + super.toString() + "," + doPhanGiai3D + "," + chieuDai + "," + chieuRong;
    }


}
