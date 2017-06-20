package com.jd.quant.core.common.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class BigDecimalUtils {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public BigDecimalUtils() {
    }

    public static boolean isZero(BigDecimal amount) {
        return null == amount ? true : amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public static BigDecimal add(BigDecimal... others) {
        if (others == null) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal tempAmount = BigDecimal.ZERO;
            BigDecimal[] arr$ = others;
            int len$ = others.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                BigDecimal single = arr$[i$];
                if (single != null) {
                    tempAmount = tempAmount.add(single);
                }
            }

            return tempAmount;
        }
    }

    public static BigDecimal sub(BigDecimal total, BigDecimal... others) {
        if (others == null) {
            return total;
        } else {
            BigDecimal leftAmount = total;
            BigDecimal[] arr$ = others;
            int len$ = others.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                BigDecimal single = arr$[i$];
                if (single != null) {
                    leftAmount = leftAmount.subtract(single);
                }
            }

            return leftAmount;
        }
    }

    public static BigDecimal divide(BigDecimal left, BigDecimal right) {
        return divide(left, right, 6, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal divide(BigDecimal left, BigDecimal right, int scale, int roundingMode) {
//        if(left == null || BigDecimalUtils.isZero(left)){
//            return BigDecimal.ZERO;
//        }
//        if(right == null || BigDecimalUtils.isZero(right)){
//            return BigDecimal.ZERO;
//        }
        return left.divide(right, scale, roundingMode);
    }

    /**
     * 默认保留四位小数
     *
     * @param value
     * @return
     */
    public static double doubleValue(BigDecimal value) {
        return doubleValue(value, 4);
    }

    /**
     * bigdecimal 转 double，Scale 保留几位小数
     *
     * @param value
     * @param Scale
     * @return
     */
    public static double doubleValue(BigDecimal value, int Scale) {
        if (value == null) {
            return 0d;
        }
        return doubleValue(value, Scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * bigdecimal 转 double，Scale 保留几位小数
     *
     * @param value
     * @param Scale
     * @return
     */
    public static double doubleValue(BigDecimal value, int Scale, int roundingMode) {
        if (value == null) {
            return 0d;
        }
        return value.setScale(Scale, roundingMode).doubleValue();
    }

    /**
     * 获取小数点位数
     *
     * @param data
     * @return
     */
    public static int getDecimals(Double data) {
        BigDecimal bd = new BigDecimal(String.valueOf(data));
        return bd.scale();
    }

    /**
     * 小数点的位数和限定的length进行对比，相等0
     * <p/>
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int compareLength(double d1, double d2) {
        if (Math.abs(getDecimals(d1) - getDecimals(d2)) > 0) {
            return 4;
        } else {
            return 2;
        }
    }

    /**
     * 计算涨幅百分比
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double calculatePercentageGains(double d1, double d2) {
        int length = compareLength(d1, d2);
        double percent = Math.abs((d1 - d2) / d1);
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(length);
        return Double.valueOf(numberFormat.format(percent));
    }

    /**
     * 涨停价格
     *
     * @param b1
     * @return
     */
    public static double limitUp(BigDecimal b1) {
        BigDecimal percent = new BigDecimal("0.1");
        //b1 = new BigDecimal(doubleValue(b1,2));
        BigDecimal calculatePercent = b1.multiply(percent);
        return doubleValue(b1.add(calculatePercent), 2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 涨停价格
     *
     * @return
     */
    public static double limitUp(BigDecimal closePrice, BigDecimal percent) {
        //b1 = new BigDecimal(doubleValue(b1,2));
        BigDecimal calculatePercent = closePrice.multiply(percent);
        return doubleValue(closePrice.add(calculatePercent), 2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 跌停价格
     *
     * @param b1
     * @return
     */
    public static double limitDown(BigDecimal b1) {
        BigDecimal percent = new BigDecimal("0.1");
        //b1 = new BigDecimal(doubleValue(b1,2));
        BigDecimal calculatePercent = b1.multiply(percent);
        return doubleValue(b1.subtract(calculatePercent), 2);
    }

    /**
     * 跌停价格
     *
     * @return
     */
    public static double limitDown(BigDecimal closePrice, BigDecimal percent) {
        //b1 = new BigDecimal(doubleValue(b1,2));
        BigDecimal calculatePercent = closePrice.multiply(percent);
        return doubleValue(closePrice.subtract(calculatePercent), 2);
    }

    public static BigDecimal multiply(BigDecimal left, BigDecimal right) {
        return left.multiply(right);
    }


    public static boolean eq(BigDecimal amount, BigDecimal other) {
        return amount.compareTo(other) == 0;
    }

    public static boolean gt(BigDecimal amount, BigDecimal other) {
        return amount.compareTo(other) > 0;
    }

    public static boolean eteq(BigDecimal amount, BigDecimal other) {
        return amount.compareTo(other) >= 0;
    }

    public static boolean lt(BigDecimal amount, BigDecimal other) {
        return amount.compareTo(other) < 0;
    }

    public static boolean lteq(BigDecimal amount, BigDecimal other) {
        return amount.compareTo(other) <= 0;
    }

    public static void main(String[] args) {
        double d1 = 13.5d;
        double d2 = 13.5d;

        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        int result = b1.compareTo(b2);
        System.out.println(result);

        double d3 = doubleValue(new BigDecimal(99.4567), 2);
        System.out.println(d3);

        System.out.println(lt(BigDecimal.valueOf(65.889), BigDecimal.valueOf(65.888)));
    }
}
