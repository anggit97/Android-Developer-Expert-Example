package com.anggitprayogo.dicoding.mytestingapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    private MainPresenter mainPresenter;
    private MainView view;

    @Before
    public void setUp() throws Exception{
        view = mock(MainView.class);
        mainPresenter = new MainPresenter(view);
    }

    @Test
    public void testVolumeWithIntegerInput()throws Exception{
        double volume = mainPresenter.volume(2,8,1);
        assertEquals(16,volume, 0.0001);
    }

    @Test
    public void testVolumeWithDoubleInput()throws Exception{
        double volume = mainPresenter.volume(2.3,8.1,1.3);
        assertEquals(24.219,volume, 0.0001);
    }

    @Test
    public void testVolumeWithZeroInput()throws Exception{
        double volume = mainPresenter.volume(0,0,0);
        assertEquals(0,volume, 0.0001);
    }

    @Test
    public void testHitungVolume() throws Exception{
        mainPresenter.hitungVolume(10,10,10);
        verify(view).tampilkanVolume(any(MainModel.class));
    }


}