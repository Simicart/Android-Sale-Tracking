package com.simicart.saletracking.customer.component;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.component.AppComponent;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.common.AppEvent;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.customer.entity.AddressEntity;

/**
 * Created by Glenn on 11/27/2016.
 */

public class AddressComponent extends AppComponent {

    protected AddressEntity mAddressEntity;
    protected TextView tvTitle;
    protected TextView tvFullNameLabel, tvStreetLabel, tvCityLabel, tvStateLabel, tvCountryLabel,
            tvPhoneLabel, tvPostCodeLabel, tvEmailLabel;
    protected TextView tvFullName, tvStreet, tvCity, tvState, tvCountry,
            tvPhone, tvPostCode, tvEmail;
    protected TableRow trEmail;
    protected boolean isShowEmail;
    protected boolean isShowTitle = false;
    protected String[] PHONE_PERM = {Manifest.permission.CALL_PHONE};

    @Override
    public View createView() {

        rootView = mInflater.inflate(R.layout.component_address, null);

        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvTitle.setTextColor(Color.BLACK);
        if (isShowTitle) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        String id = mAddressEntity.getID();
        if (Utils.validateString(id)) {
            tvTitle.setText("#" + id);
        }

        tvFullNameLabel = (TextView) rootView.findViewById(R.id.tv_full_name_label);
        tvFullNameLabel.setTextColor(Color.BLACK);
        tvFullNameLabel.setText("Full Name");

        tvStreetLabel = (TextView) rootView.findViewById(R.id.tv_street_label);
        tvStreetLabel.setTextColor(Color.BLACK);
        tvStreetLabel.setText("Street");

        tvCityLabel = (TextView) rootView.findViewById(R.id.tv_city_label);
        tvCityLabel.setTextColor(Color.BLACK);
        tvCityLabel.setText("City");

        tvStateLabel = (TextView) rootView.findViewById(R.id.tv_state_label);
        tvStateLabel.setTextColor(Color.BLACK);
        tvStateLabel.setText("State/Region");

        tvCountryLabel = (TextView) rootView.findViewById(R.id.tv_country_label);
        tvCountryLabel.setTextColor(Color.BLACK);
        tvCountryLabel.setText("Country");

        tvPhoneLabel = (TextView) rootView.findViewById(R.id.tv_phone_label);
        tvPhoneLabel.setTextColor(Color.BLACK);
        tvPhoneLabel.setText("Telephone");

        tvPostCodeLabel = (TextView) rootView.findViewById(R.id.tv_post_code_label);
        tvPostCodeLabel.setTextColor(Color.BLACK);
        tvPostCodeLabel.setText("Postcode");

        tvEmailLabel = (TextView) rootView.findViewById(R.id.tv_email_label);
        tvEmailLabel.setTextColor(Color.BLACK);
        tvEmailLabel.setText("Email");

        tvFullName = (TextView) rootView.findViewById(R.id.tv_full_name);
        tvFullName.setTextColor(Color.BLACK);
        String fullName = mAddressEntity.getFirstName() + " " + mAddressEntity.getLastName();
        if (Utils.validateString(fullName)) {
            tvFullName.setText(fullName);
        }

        tvStreet = (TextView) rootView.findViewById(R.id.tv_street);
        tvStreet.setTextColor(Color.BLACK);
        String street = mAddressEntity.getStreet();
        if (Utils.validateString(street)) {
            tvStreet.setText(street);
        }

        tvCity = (TextView) rootView.findViewById(R.id.tv_city);
        tvCity.setTextColor(Color.BLACK);
        String city = mAddressEntity.getCity();
        if (Utils.validateString(city)) {
            tvCity.setText(city);
        }

        tvState = (TextView) rootView.findViewById(R.id.tv_state);
        tvState.setTextColor(Color.BLACK);
        String state = mAddressEntity.getRegion();
        if (Utils.validateString(state)) {
            tvState.setText(state);
        }

        tvCountry = (TextView) rootView.findViewById(R.id.tv_country);
        tvCountry.setTextColor(Color.BLACK);
        String country = mAddressEntity.getCountryName();
        if (Utils.validateString(country)) {
            tvCountry.setText(country);
        }

        tvPhone = (TextView) rootView.findViewById(R.id.tv_phone);
        tvPhone.setTextColor(Color.BLACK);
        final String phone = mAddressEntity.getPhone();
        if (Utils.validateString(phone)) {
            final SpannableString spannableString = new SpannableString(phone);
            spannableString.setSpan(new URLSpan(""), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvPhone.setText(spannableString, TextView.BufferType.SPANNABLE);
        }

        tvPostCode = (TextView) rootView.findViewById(R.id.tv_post_code);
        tvPostCode.setTextColor(Color.BLACK);
        String postCode = mAddressEntity.getPostCode();
        if (Utils.validateString(postCode)) {
            tvPostCode.setText(postCode);
        }

        tvEmail = (TextView) rootView.findViewById(R.id.tv_email);
        tvEmail.setTextColor(Color.BLACK);
        String email = mAddressEntity.getEmail();
        if (Utils.validateString(email)) {
            tvEmail.setText(email);
        }

        trEmail = (TableRow) rootView.findViewById(R.id.tr_email);
        if (!isShowEmail) {
            trEmail.setVisibility(View.GONE);
        } else {
            trEmail.setVisibility(View.VISIBLE);
        }

        return rootView;

    }

    public void setShowEmail(boolean showEmail) {
        isShowEmail = showEmail;
    }

    public void setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        mAddressEntity = addressEntity;
    }
}
