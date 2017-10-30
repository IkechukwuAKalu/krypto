package com.ikechukwuakalu.krypto.cards;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ikechukwuakalu.krypto.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CardsScreenTest {

    @Rule
    public ActivityTestRule<CardsActivity> activityRule = new ActivityTestRule<>(CardsActivity.class);

    @Before
    public void setup() {
        IdlingRegistry.getInstance()
                .register(activityRule.getActivity().getIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance()
                .unregister(activityRule.getActivity().getIdlingResource());
    }

    @Test
    public void clickOnFab_OpenCreateCardView() {
        onView(withId(R.id.createNewCardFab)).perform(click());
        // check view items are shown
        onView(withId(R.id.cryptoSpinner)).check(matches(isDisplayed()));
        onView(withId(R.id.currencySpinner)).check(matches(isDisplayed()));
        onView(withId(R.id.createCardBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void createCard_OpenActionBarMenu_ClickOnDeleteAll() {
        // remove all test cards if any
        deleteTestCard();
        // Create card
        createTestCard();

        // Delete all
        deleteTestCard();
        onView(withId(R.id.noCardsTv)).check(matches(isDisplayed()));
    }

    private void createTestCard() {
        onView(withId(R.id.createNewCardFab)).perform(click());

        onView(withId(R.id.createCardBtn)).perform(click());
    }

    private void deleteTestCard() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText(R.string.delete_all)).perform(click());
    }
}