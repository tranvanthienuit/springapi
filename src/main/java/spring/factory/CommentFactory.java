package spring.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Entity.Comment;
import spring.repository.CommentRepository;

import java.util.List;

@Service
public class CommentFactory {
    @Autowired
    CommentRepository commentRepository;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteUserAndComment(String userId, String commentId) {
        Comment comment = commentRepository.findByUserIdAndCommentId(userId, commentId);
        commentRepository.delete(comment);
    }

    public void updateComment(String commentId, String content) {
        commentRepository.updateComment(commentId, content);
    }

    public List<Comment> findAllByBookId(String bookId) {
        return commentRepository.findAllByBookId(bookId);
    }
}
