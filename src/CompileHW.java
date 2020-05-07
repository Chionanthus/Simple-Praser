package homework;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CompileHW {
    Map<String,Integer> reserveword = new HashMap<>()
    {
        {
            put("if", 0);put("then", 1);put("else", 2);put("while", 3);
            put("begin", 4);put("do", 5);put("end", 6);
        }
    };
    Map<Character,Integer> seperaters = new HashMap<>()
    {
        {
            put('<', 42);put('=', 42);put('>', 42);//rop
            put(';',8);put(':', 38);
            //put('>=', 42);put('!=', 42);put(':=', 38);put('<=', 42);//特殊处理
            put('¬',41);put('∧',39);put('∨',40);
            put('+', 34);put('*', 36);
            put('(', 48);put(')', 49);
//
//            put("&",9);put("|",9);
//            put("#",10);
        }
    };


    public static void main(String[] args) throws IOException {
        BufferedReader in =new BufferedReader(new InputStreamReader
                (new FileInputStream("homework\\compileX.txt")));
        StringBuilder readdata= new StringBuilder();
        String temp;
        while((temp=in.readLine())!=null)
        {
            readdata.append(temp);
        }
        String alldata=readdata.toString();//把程序存在了一个String中，String容量足够大
        CompileHW H= new CompileHW();
        H.analyse(alldata);
    }



    void analyse(String alldata)
    {
        String[] sdata = alldata.split(" ");//全部按空格划分，略微简化
        for(int i=0;i<sdata.length;i++)
        {//处理每一个数组的元素
            String thisline=sdata[i];//这一轮的按空格划分的String
            for(int j =0;j<thisline.length();j++)
            {
                char c= thisline.charAt(j);//每次取一字符


                if(isletter(c))//第一个字符为字母
                {
                    StringBuilder temp =new StringBuilder();
                    temp.append(c);//把第一个字符加进去

                    while(j<thisline.length()-1)//防止下面越界
                    {
                        char nextchar = thisline.charAt(j + 1);
                        if(isletter(nextchar)||isdigit(nextchar))//即下一个字符也是字母或数字
                        {
                            temp.append(nextchar);
                            j++;
                        }
                        else
                        {
                            break;//即下面的字符不为数字或字母,跳出上面这个while循环
                        }
                    }//从该循环出来后，temp就为一个连续的字母或者数字了
                    String ok = temp.toString();
                    if(isreservedword(ok))
                    {
                        System.out.println("("+reserveword.get(ok) +","+ ok+")");//保留字
                    }
                    else
                    {
                        System.out.println("(56,"+ ok+")");//变量
                    }
                }
                else if(isdigit(c))//数字
                {//直到找到下一个不是数字的字符
                    StringBuilder temp =new StringBuilder();
                    temp.append(c);
                    while(j<thisline.length()-1)//防止下面越界
                    {
                        char nextchar = thisline.charAt(j + 1);
                        if(isdigit(nextchar))//即下一个字符也是数字
                        {
                            temp.append(nextchar);
                            j++;
                        }
                        else
                        {
                            break;//即下面的字符不为数字,跳出上面这个while循环
                        }
                    }//从该循环出来后，temp就为一个连续的数字了
                    String ok = temp.toString();
                    System.out.println("(57,"+ ok+")");//整常量

                }

                else if (isseperatersword(c))//要注意因为有两位的标志
                {
                    if((j!=thisline.length()-1) && (thisline.charAt(j + 1)=='='))//该符号有两位
                    {
                        if( c==':') System.out.println("(38,"+ c+"=)");
                        else System.out.println("(42,"+ c+"=)");
                        j++;//两位
                    }
                    else//只有一位的
                    {
                        System.out.println("("+seperaters.get(c)+","+ c+")");
                    }
                }

            }
        }

    }
    boolean isletter(char c)
    {
        if((c>='a'&&c<='z')||(c>='A'&&c<='Z'))
         return true;
        else return false;
    }
    boolean isdigit(char c)
    {
        if((c>='0'&&c<='9'))
            return true;
        else return false;
    }
    boolean isreservedword(String s)
    {
        if(reserveword.containsKey(s))
            return true;
        else return false;
    }
    boolean isseperatersword(char s)
    {
        if(seperaters.containsKey(s))
            return true;
        else return false;
    }



}
