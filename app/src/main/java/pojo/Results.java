package pojo;

import java.util.ArrayList;

/**
 * Created by shriroop on 10-Feb-17.
 */

public class Results {
    public String statusCode;
    public ArrayList<Product> results;

    @Override
    public String toString() {
        return "Results {" +
                " statusCode=" + statusCode +
                ", results.size()=" + results.size() + " }";
    }
}
