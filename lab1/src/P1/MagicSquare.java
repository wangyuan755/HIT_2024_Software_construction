import java.io.*;
import java.lang.*;
import java.nio.file.LinkPermission;
import java.util.*;

import static java.lang.Math.sqrt;

public class MagicSquare {
    static  boolean isInterger(Double i)
    {
        int a = (int) Math.floor(i);
        int b = (int) Math.ceil(i);
        if (a==b)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    static boolean isLegalMagicSquare(String fileName) {
        File file = new File(fileName);
        try {
            FileInputStream input = new FileInputStream(file);
            byte[] array = new byte[input.available()];
            StringBuffer buffer = new StringBuffer();
            int n = 0;
            while(true)
            {
                n = input.read();
                if(n == -1)
                {
                    break;
                }
                char by = (char)n;
                buffer.append(by);
            }
            String[] res;
            res =  buffer.toString().replace("\n","\t").split("\t+");
            double edge =  sqrt(res.length);
            if(isInterger(edge)==false)
            {
                System.out.println("矩阵不为NxN或分割符非法");
                return false;
            }

            int[][] square = new int[(int) edge][(int) edge];
            int flag = 0;
            for(int i =0;i< (int) edge;i++)
            {
                for(int j=0;j< (int) edge;j++)
                {
                    try {
                        double test = Double.parseDouble(res[flag]);

                        if (isInterger(test) == false || test < 0) {
                            System.out.println("矩阵中只因包含正整数！");
                            return false;
                        }
                        square[i][j] = (int) test;
                        flag++;
                    }catch (Exception e)
                    {
                        System.out.println("Exception: " + e);
                        return false;
                    }
                }
                if(flag >=res.length)
                {
                    break;
                }
            }

            int storge = 0;
            for(int i = 0;i<(int) edge;i++)
            {
                storge += square[0][i];
            }

            //横
            for(int i =0;i< (int) edge;i++) {
                int now =0;
                for (int j = 0; j < (int) edge; j++) {
                    now += square[i][j];
                }
                if(now != storge)
                {
                    return false;
                }
            }
            //纵
            for(int i =0;i< (int) edge;i++) {
                int now =0;
                for (int j = 0; j < (int) edge; j++) {
                    now += square[j][i];
                }
                if(now != storge)
                {
                    return false;
                }
            }
            //斜
            int now =0;
            for(int i =0;i< (int) edge;i++) {
                now += square[i][i];
            }
            if(now != storge)
            {
                return false;
            }
            now =0;
            for(int i =0;i< (int) edge;i++) {
                now += square[i][(int) edge - i -1];
            }
            if(now != storge)
            {
                return false;
            }
            input.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
            return false;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean generateMagicSquare(int n) throws FileNotFoundException {
        if(n <=0)
        {
            System.out.println("N为负数！");
            return false;
        }
        if(n%2 == 0)
        {
            System.out.println("N为偶数！");
            return false;
        }
        //经典的罗伯生成算法，首先把1放在第一行中央，之后每一个数放在前一个数的右上一格，如果已被占用就放在自己下面一格
        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;
        for (i = 1; i <= square; i++) {
            magic[row][col] = i;
            if (i % n == 0)
                row++;
            else {
                if (row == 0)
                    row = n - 1;
                else
                    row--;
                if (col == (n - 1))
                    col = 0;
                else
                    col++;
            }
        }
        try {
            File f = new File("./src/P1/txt/6.txt");
            FileOutputStream fop = new FileOutputStream(f);
            OutputStreamWriter fout = new OutputStreamWriter(fop,"UTF-8");
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++)
                    fout.append(magic[i][j] + "\t");
                fout.append("\n");
            }
            fout.close();
            fop.close();

        }
        catch (FileNotFoundException e)
        {
         System.out.println("File built failed");
         return false;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public static void main(String[] args)
    {
        System.out.println("矩阵生成：");
        try {
            generateMagicSquare(5);
        } catch (FileNotFoundException e) {
            System.out.println("生成失败！");
            throw new RuntimeException(e);
        }

        for(int i = 1;i<=6;i++) {
            System.out.println("文件"+i+":");
            if (isLegalMagicSquare("./src/P1/txt/"+i+".txt")) {
                System.out.println("true");
            } else {
                System.out.println("false");
            }
        }
    }
}
