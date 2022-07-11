package com.cqdat.master.thesis.gwoforconstruction;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class test {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
//        String url = "C:\\Users\\HOANG\\Desktop\\GWO\\source_version_9.0_Final_MOGWO_Multiple_RMCS\\source\\Data\\rand_input.txt";
//
//        int pos = 0;
//
//        // Đọc dữ liệu từ File với Scanner
//        FileInputStream fileInputStream = new FileInputStream(url);
//        Scanner scanner = new Scanner(fileInputStream);
//
//        try {
//            while (pos<10) {
//                double ss = Double.parseDouble(scanner.nextLine());
//                System.out.println(ss);
//                pos++;
//            }
//        } finally {
//            try {
//                scanner.close();
//                fileInputStream.close();
//            } catch (IOException ex) {
//
//            }
//        }

//        String[] data = {
//                "Hello Java!",
//                "Good bye!"
//        };
//        File file = new File(url);
//        OutputStream outputStream = new FileOutputStream(file);
//        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
//
//        for (int i=0;i<100000000; i++){
//            outputStreamWriter.write(Math.random()+"");
//            // Dùng để xuống hàng
//            outputStreamWriter.write("\n");
//        }
//        // Đây là phương thức quan trọng!
//        // Nó sẽ bắt chương trình chờ ghi dữ liệu xong thì mới kết thúc chương trình.
//        outputStreamWriter.flush();

        double x[] = new double[10];
        for (int i=0;i<11; i++) System.out.println(x[i]);
    }
}
