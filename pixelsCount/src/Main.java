import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static double getA0(int t0, double h0, double Ry, double Py, double dy, double d0, double Khy, double nog, double nyg, double nov, double nyv) {
        double a0 = (t0 * (Math.sqrt(Math.pow(getLyf(t0, Ry, Py, dy, d0, Khy, nog, nov, nyv), 2) + Math.pow(h0, 2)) -
                getLyf(t0, Ry, Py, dy, d0, Khy, nog, nov, nyv))) / ((t0 * Math.sqrt(Math.pow(getLyf(t0, Ry, Py, dy, d0, Khy, nog, nov, nyv), 2) +
                Math.pow(h0, 2))) + ((Ry - t0) * ((dy * nyg)/Khy))) * 100;
        return a0;
    }

    public static double getKny(int t0, double Ry, double Py, double dy, double d0, double Khy, double nog, double nov, double nyv) {
        double Kny = Py * ((getLy(dy, d0, Khy, nov, nyv) * t0) + (d0 * nog * (Ry - t0))) / (100 * Ry);
        return Kny;
    }

    public static double getLyf(int t0, double Ry, double Py, double dy, double d0, double Khy, double nog, double nov, double nyv) {
        double Lyf = getLy(dy, d0, Khy, nov, nyv) / getKny(t0, Ry, Py, dy, d0, Khy, nog, nov, nyv);
        return Lyf;
    }

    public static double getLy(double dy, double d0, double Khy, double nov, double nyv) {
        double Ly = getDp(dy, d0, nov, nyv) * Math.sqrt(4 - Math.pow(Khy, 2));
        return Ly;
    }

    public static double getHo(double dy, double d0, double Kho, double nov, double nyv) {
        double Ho = getDp(dy, d0, nov, nyv) * Kho;
        return Ho;
    }

    public static double getDp(double dy, double d0, double nov, double nyv) {
        double dp = (d0*nov + dy*nyv)/2;
        return dp;
    }

    public static ArrayList<Integer> case1(BufferedImage img, int column_num, int height, int G,
                                           ArrayList<Integer> solid_arr, String mode, boolean is_long) {

        int white_num = 255;
        int black_num = 0;
        int mode_num_1 = 0;
        int mode_num_2 = 0;

        if (mode.equals("b")) {
            mode_num_1 = black_num;
            mode_num_2 = white_num;
        } else if (mode.equals("w")) {
            mode_num_1 = white_num;
            mode_num_2 = black_num;
        }
        ArrayList<Integer> pixels = new ArrayList<>(); // i = x, i+1 = y - координаты для закрашивания
        // i+2 - количество пикселей
        int cnt = 0;
        Collections.sort(solid_arr);
        if (is_long) {
            Collections.reverse(solid_arr);
        }

        int size;
        int cnt_G = 0;

        if (solid_arr.size() <= G) {
            size = solid_arr.size();
        } else {
            size = G;
        }

        for (int j = 0; j < solid_arr.size(); j++) {
            for (int i = 0; i < height; i++) {
                Color color = new Color(img.getRGB(column_num, i));
                if (cnt_G == size) {
                    break;
                }
                if (color.getRed() == mode_num_2) {
                    cnt = 0;

                } else if (color.getRed() == mode_num_1) {
                    cnt++;
                    if (cnt == solid_arr.get(j)) {
                        if (solid_arr.size() > 1 && size == G && cnt_G == size - 1 && solid_arr.get(j) == solid_arr.get(j + 1)) {
                            size++;
                        }
                        if (i != height - 1 && new Color(img.getRGB(column_num, i+1)).getRed() == mode_num_2) {
                            if (i - cnt + 1 > 0) {
                                pixels.add(column_num);
                                pixels.add(i - cnt + 1);
                                pixels.add(cnt);
                            }
                            cnt_G++;
                        }

                    }
                }
            }
        }
        return pixels;
    }

    public static void solving(ArrayList<Integer> arr, Color color, BufferedImage result) {
        for (int i = 0; i < arr.size() - 2;  i += 3) {
            int y = arr.get(i + 1);
            for (int j = 0; j < arr.get(i + 2); j++) {
                result.setRGB(arr.get(i), y, color.getRGB());
                y++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
        Scanner in = new Scanner(System.in);

        System.out.println("Введите название файла");
        String imagePath = in.nextLine();
        BufferedImage img = ImageIO.read(new File(imagePath));

        int w = img.getWidth();
        int h = img.getHeight();
        int Ro = w;
        int Ry = h;
        int t0 = 0;
        ArrayList<Integer> t0_arr = new ArrayList<>();
        ArrayList<Integer> blacks = new ArrayList<>();
        ArrayList<Integer> whites = new ArrayList<>();

        System.out.println("Введите d0: ");
        double d0 = in.nextDouble();
        System.out.println("Введите dy: ");
        double dy = in.nextDouble();
        System.out.println("Введите Po: ");
        double P0 = in.nextDouble();
        System.out.println("Введите Py: ");
        double Py = in.nextDouble();
        System.out.println("Введите Kh.y: ");
        double Khy = in.nextDouble();
        System.out.println("Введите Kh.o: ");
        double Kho = in.nextDouble();

//        System.out.println("Введите коэффициент, учитывающий деформацию нитей основы Тo: ");
//        double To = in.nextDouble();
//        System.out.println("Введите коэффициент, учитывающий деформацию нитей утка Тy: ");
//        double Ty = in.nextDouble();
        System.out.println("Введите коэффициент, учитывающий деформацию смятия и " +
                "изменения формы поперечного сечения нитей основы по вертикали Nов: ");
        double nov = in.nextDouble();
        System.out.println("Введите коэффициент, учитывающий деформацию смятия и " +
                "изменения формы поперечного сечения нитей основы по горизонтали Nог: ");
        double nog = in.nextDouble();
        System.out.println("Введите коэффициент, учитывающий деформацию смятия и " +
                "изменения формы поперечного сечения нитей утка по вертикали Nув: ");
        double nyv = in.nextDouble();
        System.out.println("Введите коэффициент, учитывающий деформацию смятия и " +
                "изменения формы поперечного сечения нитей утка по горизонтали Nуг: ");
        double nyg = in.nextDouble();

        System.out.println("Введите критическое значение критерия Граббса Gk для числа экспериментов, равного рапорту по основе Ro = " + Ro + ":");
        double Gk = in.nextDouble();
        // создание самого excel файла в памяти
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Счёт уработки");

        // заполняем список какими-то данными
        ArrayList<DataModel> dataList = new ArrayList();

        // счетчик для строк
        int rowNum = 0;

        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue("Нить");
        row.createCell(1).setCellValue("Кличество пересечений основой нитей утка to");
        row.createCell(2).setCellValue("Уработка нитей ao");
        row.createCell(3).setCellValue("Высота волны изгиба нитей основы ho");
        row.createCell(4).setCellValue("Раппорт по основе Ro");
        row.createCell(5).setCellValue("Раппорт по утку Ry");
        row.createCell(6).setCellValue("Диаметр нитей утка dy");
        row.createCell(7).setCellValue("Плотность по основе Po");
        row.createCell(8).setCellValue("Плотность по утку Py");
        row.createCell(9).setCellValue("Фактическое расстояние между центрами нитей утка в местах пересечения их с нитями основы Ly.ф");
        row.createCell(10).setCellValue("Коэффициент наполнения ткани волокнистым материалом Kн.y");
        row.createCell(11).setCellValue("G");

        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        ArrayList<ArrayList<Integer>> solid_blacks = new ArrayList<>(); //массив кол-ва черных подряд
        ArrayList<ArrayList<Integer>> solid_whites = new ArrayList<>(); //массив кол-ва белых подряд

        for (int i = 0; i < w; i++) {
            String bufColor = null;
            String headColor = null;

            int cnt_black = 0;
            int cnt_white = 0;

            ArrayList<Integer> solid_black = new ArrayList<>();
            ArrayList<Integer> solid_white = new ArrayList<>();

            int black = 0;
            int white = 0;

            for (int j = 0; j < h; j++) {
                String col = "";
                Color color = new Color(img.getRGB(i, j));

                if (color.getRed() == 0) {
                    col = "black";
                    black++;
                    cnt_black++;

                    if (cnt_white != 0){
                        solid_white.add(cnt_white);
                        cnt_white = 0;
                    }

                    if (!col.equals(bufColor)) {
                        t0++;
                    }
                    bufColor = "black";
                    if (j == 0) {
                        t0--;
                        headColor = "black";
                    }
                } else if (color.getRed() == 255) {
                    col = "white";
                    white++;
                    cnt_white++;

                    if (cnt_black != 0){
                        solid_black.add(cnt_black);
                        cnt_black = 0;
                    }

                    if (!col.equals(bufColor)) {
                        t0++;
                    }
                    bufColor = "white";
                    if (j == 0) {
                        t0--;
                        headColor = "white";
                    }
                }
                if (j == h-1 && !col.equals(headColor)) {
                    t0++;
                }
                result.setRGB(i, j, color.getRGB());

            }
            blacks.add(black);
            whites.add(white);

            solid_blacks.add(solid_black);
            solid_whites.add(solid_white);

            dataList.add(new DataModel(i+1, t0, getA0(t0, getHo(dy, d0, Kho, nov, nyv), Ry, Py, dy, d0, Khy, nog, nyg, nov, nyv),
                    getHo(dy, d0, Kho, nov, nyv), Ro, Ry, dy, P0, Py, getKny(t0, Ry, Py, dy, d0, Khy, nog, nov, nyv),
                    getLyf(t0, Ry, Py, dy, d0, Khy, nog, nov, nyv), 0));
            t0_arr.add(t0);
            t0 = 0;

        }

        double t0_average = t0_arr.stream().mapToInt(e -> e).average().getAsDouble();

        ArrayList<Integer> extra_strands = new ArrayList<>();
        double sum = 0;
        for (int to : t0_arr) {
            sum += Math.pow(to - t0_average, 2);
        }
        double S = Math.sqrt(sum / (t0_arr.size() - 1));
        for (int to : t0_arr) {
            double Gh = Math.abs(t0_average - to) / S;
            if (Gh > Gk) {
                extra_strands.add(to);
            }
        }
        try {
            t0_average = (t0_arr.stream().mapToInt(e -> e).sum() -
                    extra_strands.stream().mapToInt(e -> e).sum()) /
                    (t0_arr.size() - extra_strands.size());
        } catch (ArithmeticException e) {
            System.out.println("Возникла ошибка :(");
        }


        double t0_6percent = 0.06 * t0_average;
        double t0_min = t0_average - t0_6percent;
        double t0_max = t0_average + t0_6percent;

        Color red = new Color(255, 0, 0);
        Color blue = new Color(0, 0, 255);

        for (int t = 0; t < t0_arr.size(); t++) {
            try {
                if (t0_min <= t0_arr.get(t) && t0_arr.get(t) <= t0_max) {
                    continue;
                } else if (t0_arr.get(t) < t0_min) {
                    int G1 = (int)Math.round(t0_min - t0_arr.get(t));
                    dataList.get(t).setG(G1);

                    if (blacks.get(t) > whites.get(t)) {
                        ArrayList<Integer> arr = case1(img, t, h, G1, solid_blacks.get(t), "b", true);
                        solving(arr, red, result);

                    } else if (blacks.get(t) < whites.get(t)) {
                        ArrayList<Integer> arr = case1(img, t, h, G1, solid_whites.get(t), "w", true);
                        solving(arr, blue, result);

                    } else if (blacks.get(t) == whites.get(t)) {
                        ArrayList<Integer> arr1 = case1(img, t, h, G1/2, solid_blacks.get(t), "b", true);
                        ArrayList<Integer> arr2 = case1(img, t, h, G1/2, solid_whites.get(t), "w", true);
                        solving(arr1, red, result);
                        solving(arr2, blue, result);
                    }
                } else if (t0_arr.get(t) > t0_max) {

                    int G2 = (int)Math.round(t0_arr.get(t) - t0_max);
                    dataList.get(t).setG(G2);

                    if ((double) blacks.get(t) > (double) whites.get(t)) {
                        ArrayList<Integer> arr = case1(img, t, h, G2, solid_blacks.get(t), "b", false);
                        solving(arr, red, result);

                    } else if ((double) blacks.get(t) < (double) whites.get(t)) {
                        ArrayList<Integer> arr = case1(img, t, h, G2, solid_whites.get(t), "w", false);
                        solving(arr, blue, result);

                    } else if ((double) blacks.get(t) == (double) whites.get(t)) {
                        ArrayList<Integer> arr1 = case1(img, t, h, G2/2, solid_blacks.get(t), "b", false);
                        ArrayList<Integer> arr2 = case1(img, t, h, G2/2, solid_whites.get(t), "w", false);
                        solving(arr1, red, result);
                        solving(arr2, blue, result);
                    }

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        File output = new File("RESULT.png");
        ImageIO.write(result, "png", output);

        // заполняем лист данными
        for (DataModel dataModel : dataList) {
            ExcelWorker.createSheetHeader(sheet, ++rowNum, dataModel);
        }

        // записываем созданный в памяти Excel документ в файл
        try (FileOutputStream out = new FileOutputStream(new File("Расчёт уработки.xls"))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Excel файл успешно создан!");
	    System.out.println("Нажмите ENTER, чтобы закрыть программу");
        System.console().readLine();
    }
}
