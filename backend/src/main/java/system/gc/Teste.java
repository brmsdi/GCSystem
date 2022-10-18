package system.gc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class Teste {
    public static void main(String[] args) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("America/Manaus"));
        System.out.println(zonedDateTime);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            System.out.println(simpleDateFormat.parse("2022-01-01"));
        } catch (ParseException e) {
            
            e.printStackTrace();
        }

        ZonedDateTime zonedDateTime2 = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("America/Manaus"));
        System.out.println(zonedDateTime2);

        System.out.println(Date.from(zonedDateTime2.toInstant()));


        ZonedDateTime zonedDateTime3 = ZonedDateTime.of(LocalDateTime.of(LocalDate.of(2022, 01, 1), LocalTime.of(8, 00)), ZoneId.of("America/Manaus"));
        System.out.println(zonedDateTime3);
        System.out.println(Date.from(zonedDateTime3.toInstant()));
    }
}
