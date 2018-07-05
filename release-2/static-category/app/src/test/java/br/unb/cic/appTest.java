package br.unb.cic;

import org.junit.Test;

import static org.junit.Assert.*;

public class appTest {
    @Test
    public void firstTest() {
        assertFalse(1 < 0);
    }

    @Test
    public void secondTest() {
        assertTrue(1 > 0);
    }

}