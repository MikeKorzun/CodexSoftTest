package by.service;

import by.entity.Comment;
import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    Comment getComment(int id);
    void deleteComment(int id);
    List<Comment> getAllByTask_Id(int id);
}
