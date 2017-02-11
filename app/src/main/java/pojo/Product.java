package pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shriroop on 10-Feb-17.
 */

public class Product extends BaseObservable implements Parcelable {
    public String brandName;
    public String thumbnailImageUrl;
    public String productId;
    public String originalPrice;
    public String styleId;
    public String colorId;
    public String price;
    public String percentOff;
    public String productUrl;
    public String productName;

    protected Product(Parcel in) {
        brandName = in.readString();
        thumbnailImageUrl = in.readString();
        productId = in.readString();
        originalPrice = in.readString();
        styleId = in.readString();
        colorId = in.readString();
        price = in.readString();
        percentOff = in.readString();
        productUrl = in.readString();
        productName = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Bindable
    public String getBrandName() {
        return brandName;
    }

    @Bindable
    public String getProductName() {
        return productName;
    }

    @Bindable
    public String getPercentOff() {
        return percentOff;
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    @Bindable
    public String getOriginalPrice() {
        return originalPrice;
    }

    @Override
    public String toString() {
        return "Product {" +
                " brandName=" + brandName +
                ", thumbnailImageUrl=" + thumbnailImageUrl +
                ", productId=" + productId +
                ", originalPrice=" + originalPrice +
                ", styleId=" + styleId +
                ", colorId=" + colorId +
                ", price=" + price +
                ", percentOff=" + percentOff +
                ", productUrl=" + productUrl +
                ", productName=" + productName + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {dest.writeString(brandName);
        dest.writeString(thumbnailImageUrl);
        dest.writeString(productId);
        dest.writeString(originalPrice);
        dest.writeString(styleId);
        dest.writeString(colorId);
        dest.writeString(price);
        dest.writeString(percentOff);
        dest.writeString(productUrl);
        dest.writeString(productName);
    }
}
