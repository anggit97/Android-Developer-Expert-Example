package com.anggitprayogo.dicoding.learnespresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureTextChangeWork(){
        onView(withId(R.id.et_text)).perform(typeText("HELLO"), closeSoftKeyboard());
        onView(withId(R.id.btn_change_text)).perform(click());
        onView(withId(R.id.tv_hasil)).check(matches(withText("HELLO")));
    }

    @Test
    public void ensureMoveActivityWithData(){
        onView(withId(R.id.et_text)).perform(typeText("MenjadiAndroidDeveloperExpert"), closeSoftKeyboard());
        onView(withId(R.id.btn_swicth_activity)).perform(click());
        onView(withId(R.id.tv_result_view)).check(matches(withText("MenjadiAndroidDeveloperExpert")));
    }

}
