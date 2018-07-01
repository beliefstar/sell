package com.zhenxin.sell.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Demo {

    //存放测试数据
    static List<Item> yearData = new ArrayList<>();

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        //生成测试数据
        gen();

        //按年搜索数据，按月分类
        List<Item> items = fn("2017");

        //打印结果
        for (Item item : items) {
            String m = sdf.format(item.getDateStamp());
            String count = Integer.toString(item.getCount());
            System.out.printf("日期：%s,值：%s\n", m, count);
        }
    }


    static List<Item> fn(String year) {
        //生成12个
        List<Item> items = init(year);

        Calendar calendar = Calendar.getInstance();

        for (Item v : yearData) {
            calendar.setTime(v.getDateStamp());
            int index = calendar.get(Calendar.MONTH);
            Item item = items.get(index);
            Integer count = item.getCount();
            count += v.getCount();
            item.setCount(count);
        }
        return items;
    }

    static List<Item> init(String year) {

        List<Item> res = new ArrayList<>();

        for (int i = 0; i < 12; i++) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.parseInt(year));
            calendar.set(Calendar.MONTH, i);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            Item item = new Item(calendar.getTime(), 0);
            res.add(item);
        }

        return res;
    }

    static void gen() {
        Random random = new Random();

        String y = "2017";

        for (int i = 0; i < 50; i++) {

            String m = Integer.toString(random.nextInt(12) + 1);
            m = m.length() > 1 ? m : "0" + m;
            String d = Integer.toString(random.nextInt(31) + 1);
            d = d.length() > 1 ? d : "0" + d;

            if ("02".equals(m)) {
                d = "05";
            }

            String dateStr = y + "-" + m + "-" + d;
            //System.out.println(dateStr);

            try {
                Item item = new Item(sdf.parse(dateStr), random.nextInt(10));
                yearData.add(item);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    static class Item {
        Date dateStamp;

        Integer count;

        public Item(Date dateStamp, Integer count) {
            this.dateStamp = dateStamp;
            this.count = count;
        }

        public Date getDateStamp() {
            return dateStamp;
        }

        public void setDateStamp(Date dateStamp) {
            this.dateStamp = dateStamp;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
