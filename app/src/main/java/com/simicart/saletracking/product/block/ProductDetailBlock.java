package com.simicart.saletracking.product.block;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.common.Constants;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.product.adapter.ProductImagesAdapter;
import com.simicart.saletracking.product.entity.ProductAttributeEntity;
import com.simicart.saletracking.product.entity.ProductEntity;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/7/2016.
 */

public class ProductDetailBlock extends AppBlock {

    protected TextView tvInfoTitle, tvImagesTitle, tvShortDescriptionTitle, tvDescriptionTitle, tvAdditionalTitle;
    protected TextView tvProductIDLabel, tvProductSkuLabel, tvProductUrlLabel, tvProductTypeLabel,
            tvProductPriceLabel, tvProductVisibilityLabel;
    protected TextView tvProductID, tvProductSku, tvProductUrl, tvProductType, tvProductPrice, tvProductVisibility;
    protected TextView tvProductName, tvShortDescription, tvShortDescriptionDetail, tvDescription, tvDescriptionDetail;
    protected ImageView ivEditInfo, ivEditShortDescription, ivEditDescription;
    protected RecyclerView rvImages;
    protected LinearLayout llAdditional;
    protected RelativeLayout rlViewDetailDescription, rlViewDetailShortDescription;
    protected ProductEntity mProduct;

    public ProductDetailBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {
        initInfo();
        initImages();
        initShortDescription();
        initDescription();
        initAdditional();
    }

    @Override
    public void updateView(AppCollection collection) {
        if (collection != null) {
            if (collection.containKey("product")) {
                mProduct = (ProductEntity) collection.getDataWithKey("product");
                if (mProduct != null) {
                    showInfo();
                    showImages();
                    showShortDescription();
                    showDescription();
                    showAdditional();
                }
            } else {
                mView.setVisibility(View.INVISIBLE);
            }
        } else {
            mView.setVisibility(View.INVISIBLE);
        }
    }

    protected void initInfo() {
        tvInfoTitle = (TextView) mView.findViewById(R.id.tv_info_title);
        tvInfoTitle.setTextColor(Color.BLACK);
        tvInfoTitle.setText("PRODUCT INFORMATION");

        ivEditInfo = (ImageView) mView.findViewById(R.id.iv_edit_info);
        if(!AppManager.getInstance().getCurrentUser().hasPermission(Constants.Permission.PRODUCT_EDIT)) {
            ivEditInfo.setVisibility(View.GONE);
        } else {
            ivEditInfo.setVisibility(View.VISIBLE);
        }

        tvProductName = (TextView) mView.findViewById(R.id.tv_product_name);
        tvProductName.setTextColor(Color.BLACK);

        tvProductIDLabel = (TextView) mView.findViewById(R.id.tv_product_id_label);
        tvProductIDLabel.setTextColor(Color.BLACK);
        tvProductIDLabel.setText("Id");

        tvProductID = (TextView) mView.findViewById(R.id.tv_product_id);
        tvProductID.setTextColor(Color.BLACK);

        tvProductSkuLabel = (TextView) mView.findViewById(R.id.tv_product_sku_label);
        tvProductSkuLabel.setTextColor(Color.BLACK);
        tvProductSkuLabel.setText("Sku");

        tvProductSku = (TextView) mView.findViewById(R.id.tv_product_sku);
        tvProductSku.setTextColor(Color.BLACK);

        tvProductUrlLabel = (TextView) mView.findViewById(R.id.tv_product_url_label);
        tvProductUrlLabel.setTextColor(Color.BLACK);
        tvProductUrlLabel.setText("Url");

        tvProductUrl = (TextView) mView.findViewById(R.id.tv_product_url);
        tvProductUrl.setTextColor(Color.BLACK);

        tvProductTypeLabel = (TextView) mView.findViewById(R.id.tv_product_type_label);
        tvProductTypeLabel.setTextColor(Color.BLACK);
        tvProductTypeLabel.setText("Type");

        tvProductType = (TextView) mView.findViewById(R.id.tv_product_type);
        tvProductType.setTextColor(Color.BLACK);

        tvProductPriceLabel = (TextView) mView.findViewById(R.id.tv_product_price_label);
        tvProductPriceLabel.setTextColor(Color.BLACK);
        tvProductPriceLabel.setText("Price");

        tvProductPrice = (TextView) mView.findViewById(R.id.tv_product_price);
        tvProductPrice.setTextColor(Color.BLACK);

        tvProductVisibilityLabel = (TextView) mView.findViewById(R.id.tv_product_visibility_label);
        tvProductVisibilityLabel.setTextColor(Color.BLACK);
        tvProductVisibilityLabel.setText("Visibility");

        tvProductVisibility = (TextView) mView.findViewById(R.id.tv_product_visibility);
        tvProductVisibility.setTextColor(Color.BLACK);
    }

    protected void initImages() {
        tvImagesTitle = (TextView) mView.findViewById(R.id.tv_images_title);
        tvImagesTitle.setTextColor(Color.BLACK);
        tvImagesTitle.setText("PRODUCT IMAGES");

        rvImages = (RecyclerView) mView.findViewById(R.id.rv_product_images);
        rvImages.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvImages.setNestedScrollingEnabled(false);
    }

    protected void initShortDescription() {
        tvShortDescriptionTitle = (TextView) mView.findViewById(R.id.tv_short_description_title);
        tvShortDescriptionTitle.setTextColor(Color.BLACK);
        tvShortDescriptionTitle.setText("SHORT DESCRIPTION");

        ivEditShortDescription = (ImageView) mView.findViewById(R.id.iv_edit_short_description);
        if(!AppManager.getInstance().getCurrentUser().hasPermission(Constants.Permission.PRODUCT_EDIT)) {
            ivEditShortDescription.setVisibility(View.GONE);
        } else {
            ivEditShortDescription.setVisibility(View.VISIBLE);
        }

        tvShortDescription = (TextView) mView.findViewById(R.id.tv_short_description);
        tvShortDescription.setTextColor(Color.BLACK);

        tvShortDescriptionDetail = (TextView) mView.findViewById(R.id.tv_view_detail_short_description);
        tvShortDescriptionDetail.setTextColor(Color.BLACK);
        tvShortDescriptionDetail.setText("View Detail");

        rlViewDetailShortDescription = (RelativeLayout) mView.findViewById(R.id.rl_view_detail_short_description);
    }

    protected void initDescription() {
        tvDescriptionTitle = (TextView) mView.findViewById(R.id.tv_description_title);
        tvDescriptionTitle.setTextColor(Color.BLACK);
        tvDescriptionTitle.setText("DESCRIPTION");

        ivEditDescription = (ImageView) mView.findViewById(R.id.iv_edit_description);
        if(!AppManager.getInstance().getCurrentUser().hasPermission(Constants.Permission.PRODUCT_EDIT)) {
            ivEditDescription.setVisibility(View.GONE);
        } else {
            ivEditDescription.setVisibility(View.VISIBLE);
        }

        tvDescription = (TextView) mView.findViewById(R.id.tv_description);
        tvDescription.setTextColor(Color.BLACK);

        tvDescriptionDetail = (TextView) mView.findViewById(R.id.tv_view_detail_description);
        tvDescriptionDetail.setTextColor(Color.BLACK);
        tvDescriptionDetail.setText("View Detail");

        rlViewDetailDescription = (RelativeLayout) mView.findViewById(R.id.rl_view_detail_description);
    }

    protected void initAdditional() {
        tvAdditionalTitle = (TextView) mView.findViewById(R.id.tv_additional_title);
        tvAdditionalTitle.setTextColor(Color.BLACK);
        tvAdditionalTitle.setText("ADDITIONAL");

        llAdditional = (LinearLayout) mView.findViewById(R.id.ll_product_additional);
    }

    protected void showInfo() {
        String productName = mProduct.getName();
        if (Utils.validateString(productName)) {
            tvProductName.setText(productName);
        }

        String productID = mProduct.getID();
        if (Utils.validateString(productID)) {
            tvProductID.setText(productID);
        }

        String productSku = mProduct.getSku();
        if (Utils.validateString(productSku)) {
            tvProductSku.setText(productSku);
        }

        String price = mProduct.getPrice();
        if (Utils.validateString(price)) {
            tvProductPrice.setText(Utils.getPrice(price, "USD"));
        }

        String productType = mProduct.getType();
        if (Utils.validateString(productType)) {
            tvProductType.setText(productType);
        }

        String productVisibility = mProduct.getVisibility();
        if (Utils.validateString(productVisibility)) {
            tvProductVisibility.setText(productVisibility);
        }
    }

    protected void showImages() {
        ArrayList<String> images = mProduct.getProductImages();
        if (images != null) {
            ProductImagesAdapter adapter = new ProductImagesAdapter(images);
            rvImages.setAdapter(adapter);
        }
    }

    protected void showShortDescription() {
        String shortDescription = mProduct.getShortDescription();
        if (Utils.validateString(shortDescription)) {
            Utils.setTextHtml(tvShortDescription, shortDescription);
        }
    }

    protected void showDescription() {
        String description = mProduct.getDescription();
        if (Utils.validateString(description)) {
            Utils.setTextHtml(tvDescription, description);
        }
    }

    protected void showAdditional() {
        ArrayList<ProductAttributeEntity> listAttributes = mProduct.getListAttributes();
        if (listAttributes != null) {
            for (ProductAttributeEntity attribute : listAttributes) {
                String label = attribute.getLabel();
                String value = attribute.getValue();

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                if (Utils.validateString(label)) {
                    TextView tv_title = new TextView(mContext);
                    tv_title.setLayoutParams(params);
                    tv_title.setText(label);
                    tv_title.setTypeface(Typeface.DEFAULT_BOLD);
                    tv_title.setTextColor(Color.BLACK);
                    tv_title.setPadding(0, Utils.toPixel(10), 0, 0);
                    llAdditional.addView(tv_title);
                }

                if (Utils.validateString(value)) {
                    WebView webView = new WebView(mContext);
                    webView.setLayoutParams(params);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    webView.getSettings().setLoadWithOverviewMode(true);
                    webView.getSettings().setUseWideViewPort(true);
                    webView.getSettings().setBuiltInZoomControls(false);
                    webView.getSettings().setDisplayZoomControls(false);
                    webView.getSettings().setLoadsImagesAutomatically(true);
                    webView.getSettings().setSupportZoom(false);
                    webView.setVerticalScrollBarEnabled(false);
                    webView.setHorizontalScrollBarEnabled(false);
                    webView.loadDataWithBaseURL(
                            null,
                            StringHTML("<html><body style=\"color:" + "#000000"
                                    + ";font-size:14px;\"'background-color:transparent' >"
                                    + "<p align=\"justify\">"
                                    + value
                                    + "</p>"
                                    + "</body></html>"), "text/html", "charset=UTF-8", null);
                    llAdditional.addView(webView);
                }

            }
        }
    }

    protected String StringHTML(String html) {
        String head = "<head><meta content='text/html; charset=UTF-8' http-equiv='Content-Type' /><style type='text/css'>body{font-size: 16px}</style></head>";
        String HtmlString = "<html>" + head + "<body>" + html
                + "</body></html>";
        return HtmlString;
    }

    public void setViewDetailDescriptionClick(View.OnClickListener listener) {
        rlViewDetailDescription.setOnClickListener(listener);
    }

    public void setViewDetailShortDescriptionClick(View.OnClickListener listener) {
        rlViewDetailShortDescription.setOnClickListener(listener);
    }

    public void setEditShortDescriptionClick(View.OnClickListener listener) {
        ivEditShortDescription.setOnClickListener(listener);
    }

    public void setEditDescriptionClick(View.OnClickListener listener) {
        ivEditDescription.setOnClickListener(listener);
    }

}
