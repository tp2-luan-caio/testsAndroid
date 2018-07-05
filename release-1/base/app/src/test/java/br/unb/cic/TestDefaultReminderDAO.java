package br.unb.cic;

import android.app.Activity;
import android.content.Context;

import org.junit.Test;

import java.util.List;

import br.unb.cic.framework.persistence.DBException;
import br.unb.cic.framework.persistence.GenericDAO;
import br.unb.cic.reminders.controller.Controller;
import br.unb.cic.reminders.model.Reminder;
import br.unb.cic.reminders.model.db.DBFactory;
import br.unb.cic.reminders.model.db.DefaultReminderDAO;
import br.unb.cic.reminders.view.AddReminderActivity;
import br.unb.cic.reminders.view.ReminderActivity;

import static org.junit.Assert.*;

public class TestDefaultReminderDAO {
    /*@Test
    public void thirdTest() {
        Reminder r = new Reminder((long)1, "Esse é um teste");


        assertTrue(r.getId() == 1);
        assertEquals(r.getText(), "Esse é um teste");

    }*/

    @Test
    public void TestAddinBD() throws DBException {
        Context c = new Activity();
        DefaultReminderDAO d = new DefaultReminderDAO(c);
        Reminder r = new Reminder((long)1, "Esse é um teste");

        d.saveReminder(r);

        List<Reminder> l = d.listReminders();
        assertEquals(l.get(0).getText(), "Esse é um teste");
        assertTrue(l.get(0).getId() == 1);
    }
}
