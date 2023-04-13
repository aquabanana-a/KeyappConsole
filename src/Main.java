import com.aurora.gplayapi.data.models.AuthData;
import com.aurora.gplayapi.data.models.Review;
import com.aurora.gplayapi.helpers.AuthHelper;
import com.aurora.gplayapi.helpers.ReviewsHelper;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String argsString = String.join("", args);
        class Param {
            public String header;
            public String value;
            Param(String header) { this.header = header; }
        }

        ArrayList<Param> params = new ArrayList<>();
        params.add(new Param("-email["));
        params.add(new Param("-token["));
        params.add(new Param("-doc["));
        params.add(new Param("-title["));
        params.add(new Param("-content["));
        params.add(new Param("-rating["));

        for(int p=0; p<params.size(); p++) {
            String header = params.get(p).header;
            int paramStartIndex = argsString.indexOf(header) + header.length();
            int paramEndIndex = argsString.length() - 1;
            for (int i = paramStartIndex; i < argsString.length() - 1; i++) {
                if (argsString.charAt(i) == ']') {
                    paramEndIndex = i;
                    break;
                }
            }
            params.get(p).value = argsString.substring(paramStartIndex, paramEndIndex);
        }

        String email = params.get(0).value;
        String aastoken = params.get(1).value;
        String packageName = params.get(2).value;
        String title = params.get(3).value;
        String content = params.get(4).value;
        int rating = Integer.parseInt(params.get(5).value);
        boolean isBeta = false;

        System.out.println("email=" + email);
        System.out.println("aastoken=" + aastoken);
        System.out.println("packageName=" + packageName);
        System.out.println("title=" + title);
        System.out.println("content=" + content);
        System.out.println("rating=" + rating);
        System.out.println("isBeta=" + isBeta);

        try {
            AuthData authData = AuthHelper.Companion.build(email, aastoken);
            ReviewsHelper helper = new ReviewsHelper(authData);

            Review review = helper.addOrEditReview(packageName, title, content, rating, isBeta);

            System.out.println("Done! Comment id=" + review.getCommentId());
        }
        catch (Exception e) {
            System.out.println("Fault :(");
        }
    }
}