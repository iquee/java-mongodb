package com.mongodb.m101j.course;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class BlogPostDAO {
    MongoCollection<Document> postsCollection;

    public BlogPostDAO(final MongoDatabase blogDatabase) {
        postsCollection = blogDatabase.getCollection("posts");
    }

    // Return a single post corresponding to a permalink
    public Document findByPermalink(String permalink) {

        // XXX HW 3.2,  Work Here
        Document post = new Document();
        post.append("permalink", permalink);

        FindIterable<Document> posts = postsCollection.find(post);
        return posts.first();
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<Document> findByDateDescending(int limit) {

        // XXX HW 3.2,  Work Here
        // Return a list of DBObjects, each one a post from the posts collection
        ArrayList<Document> posts = postsCollection.find().sort(new Document("_id", -1))
                .limit(limit).into(new ArrayList<Document>());
        return posts;
    }


    public String addPost(String title, String body, List tags, String username) {

        System.out.println("inserting blog entry " + title + " " + body);

        String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();


        // XXX HW 3.2, Work Here
        // Remember that a valid post has the following keys:
        // author, body, permalink, tags, comments, date, title
        //
        // A few hints:
        // - Don't forget to create an empty list of comments
        // - for the value of the date key, today's datetime is fine.
        // - tags are already in list form that implements suitable interface.
        // - we created the permalink for you above.

        // Build the post object and insert it
        Document post = new Document();
        post.append("title", title);
        post.append("body", body);
        post.append("author", username);
        post.append("permalink", permalink);
        post.append("tags", tags);
        post.append("comments", Arrays.asList());
        post.append("date", new Date());

        postsCollection.insertOne(post);

        return permalink;
    }




    // White space to protect the innocent








    // Append a comment to a blog post
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {

        // XXX HW 3.3, Work Here
        // Hints:
        // - email is optional and may come in NULL. Check for that.
        // - best solution uses an update command to the database and a suitable
        //   operator to append the comment on to any existing list of comments

        Document comment = new Document();
        comment.append("author", name);
        comment.append("body", body);
        comment.append("email", (email == null) ? " " : email);

        Document post = findByPermalink(permalink);
        ArrayList<Document> comments = (ArrayList<Document>) post.get("comments");
        comments.add(comment);
        post.append("comments", comments);

        Bson filter = eq("_id", post.getObjectId("_id"));
        Bson update =  new Document("$set", post);
        UpdateOptions options = new UpdateOptions().upsert(true);

        postsCollection.updateOne(filter, update, options);
    }
}
