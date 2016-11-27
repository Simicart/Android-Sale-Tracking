package com.simicart.saletracking.customer.component;

import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.component.AppComponent;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.customer.entity.AddressEntity;

/**
 * Created by Glenn on 11/27/2016.
 */

public class AddressComponent extends AppComponent {

    protected AddressEntity mAddressEntity;
    protected TextView tvFullNameLabel, tvStreetLabel, tvCityLabel, tvStateLabel, tvCountryLabel,
            tvPhoneLabel, tvPostCodeLabel, tvEmailLabel;
    protected TextView tvFullName, tvStreet, tvCity, tvState, tvCountry,
            tvPhone, tvPostCode, tvEmail;
    protected TableRow trEmail;
    protected boolean isShowEmail;

    @Override
    public View createView() {

        rootView = mInflater.inflate(R.layout.component_address, null);

        tvFullNameLabel = (TextView) rootView.findViewById(R.id.tv_full_name_label);
        tvFullNameLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvFullNameLabel.setText("Full Name");

        tvStreetLabel = (TextView) rootView.findViewById(R.id.tv_street_label);
        tvStreetLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvStreetLabel.setText("Street");

        tvCityLabel = (TextView) rootView.findViewById(R.id.tv_city_label);
        tvCityLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvCityLabel.setText("City");

        tvStateLabel = (TextView) rootView.findViewById(R.id.tv_state_label);
        tvStateLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvStateLabel.setText("State/Region");

        tvCountryLabel = (TextView) rootView.findViewById(R.id.tv_country_label);
        tvCountryLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvCountryLabel.setText("Country");

        tvPhoneLabel = (TextView) rootView.findViewById(R.id.tv_phone_label);
        tvPhoneLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvPhoneLabel.setText("Telephone");

        tvPostCodeLabel = (TextView) rootView.findViewById(R.id.tv_post_code_label);
        tvPostCodeLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvPostCodeLabel.setText("Postcode");

        tvEmailLabel = (TextView) rootView.findViewById(R.id.tv_email_label);
        tvEmailLabel.setTextColor(AppColor.getInstance().getBlackColor());
        tvEmailLabel.setText("Email");

        tvFullName = (TextView) rootView.findViewById(R.id.tv_full_name);
        tvFullName.setTextColor(AppColor.getInstance().getBlackColor());
        String fullName = mAddressEntity.getFirstName() + " " + mAddressEntity.getLastName();
        if(Utils.validateString(fullName)) {
            tvFullName.setText(fullName);
        }

        tvStreet = (TextView) rootView.findViewById(R.id.tv_street);
        tvStreet.setTextColor(AppColor.getInstance().getBlackColor());
        String street = mAddressEntity.getStreet();
        if(Utils.validateString(street)) {
            tvStreet.setText(street);
        }

        tvCity = (TextView) rootView.findViewById(R.id.tv_city);
        tvCity.setTextColor(AppColor.getInstance().getBlackColor());
        String city = mAddressEntity.getCity();
        if(Utils.validateString(city)) {
            tvCity.setText(city);
        }

        tvState = (TextView) rootView.findViewById(R.id.tv_state);
        tvState.setTextColor(AppColor.getInstance().getBlackColor());
        String state = mAddressEntity.getRegion();
        if(Utils.validateString(state)) {
            tvState.setText(state);
        }

        tvCountry = (TextView) rootView.findViewById(R.id.tv_country);
        tvCountry.setTextColor(AppColor.getInstance().getBlackColor());
        String country = mAddressEntity.getCountryName();
        if(Utils.validateString(country)) {
            tvCountry.setText(country);
        }

        tvPhone = (TextView) rootView.findViewById(R.id.tv_phone);
        tvPhone.setTextColor(AppColor.getInstance().getBlackColor());
        String phone = mAddressEntity.getPhone();
        if(Utils.validateString(phone)) {
            tvPhone.setText(phone);
        }

        tvPostCode = (TextView) rootView.findViewById(R.id.tv_post_code);
        tvPostCode.setTextColor(AppColor.getInstance().getBlackColor());
        String postCode = mAddressEntity.getPostCode();
        if(Utils.validateString(postCode)) {
            tvPostCode.setText(postCode);
        }

        tvEmail = (TextView) rootView.findViewById(R.id.tv_email);
        tvEmail.setTextColor(AppColor.getInstance().getBlackColor());
        String email = mAddressEntity.getEmail();
        if(Utils.validateString(email)) {
            tvEmail.setText(email);
        }

        trEmail = (TableRow) rootView.findViewById(R.id.tr_email);
        if(!isShowEmail) {
            trEmail.setVisibility(View.GONE);
        } else {
            trEmail.setVisibility(View.VISIBLE);
        }

        return rootView;

    }

    public void setShowEmail(boolean showEmail) {
        isShowEmail = showEmail;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        mAddressEntity = addressEntity;
    }
}
