package by.uniterra.dai.eao;

import javax.persistence.EntityManager;

import by.uniterra.dai.entity.CalendarSpecialDay;

public class CalendarSpecialDayEAO extends ServiceBaseEAO<CalendarSpecialDay>
{

    public CalendarSpecialDayEAO(EntityManager em)
    {
        super(em, CalendarSpecialDay.class);
    }

}
