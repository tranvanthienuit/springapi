package spring.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Blog")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(generator = "uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "BlogId", updatable = false)
    private String blogId;
    @Column(name = "title")
    private String title;
    @Column(name = "context")
    @Lob
    private byte[] context;
    @Column(name = "content")
    @Lob
    private byte[] content;
    @Column(name = "DayAdd")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dayAdd;
    @Column(name = "image")
    @Lob
    private byte[] image;
    //    @Column(name = "nameAuthor")
//    private String nameAuthor;
    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    public void setImage(String image) {
        this.image = image.getBytes();
    }

    public String getImage() {
        if (image == null)
            return null;
        return new String(image);
    }

    public void setContent(String content) {
        this.content = content.getBytes();
    }

    public String getContent() {
        if (content == null)
            return null;
        return new String(content);
    }

    public void setContext(String content) {
        this.context = content.getBytes();
    }

    public String getContext() {
        if (context == null)
            return null;
        return new String(context);
    }
}