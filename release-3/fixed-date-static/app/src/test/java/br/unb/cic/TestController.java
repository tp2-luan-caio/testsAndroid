package br.unb.cic;

import android.app.Activity;
import android.content.Context;

import org.junit.Test;

import java.util.List;

import br.unb.cic.reminders.controller.Controller;
import br.unb.cic.reminders.model.Reminder;

import static org.junit.Assert.assertEquals;

public class TestController {
    @Test
    public void TestController1() throws Exception {
        Context c = new Activity();
        Controller controller = Controller.instance(c);

        Reminder r = new Reminder((long) 1, "Esse é um teste");

        controller.addReminder(r);
        List<Reminder> l = controller.listReminders();

        assertEquals(l.get(0).getText(), "Esse é um teste");
     }
}