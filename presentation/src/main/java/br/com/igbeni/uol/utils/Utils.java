package br.com.igbeni.uol.utils;

public class Utils {

    public static String formatDate(Long time) {
        String timeAsString = Long.toString(time);

        String year = timeAsString.substring(0, 4);
        String month = timeAsString.substring(4, 6);
        String day = timeAsString.substring(6, 8);

        String hour = timeAsString.substring(8, 10);
        String minute = timeAsString.substring(10, 12);

        return day + '/' + month + '/' + year + ' ' + hour + 'h' + minute;
    }
}
