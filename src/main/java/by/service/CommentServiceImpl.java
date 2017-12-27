package by.service;

import by.dao.CommentRepository;
import by.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;

    @Override
    @Transactional
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment getComment(int id) {
        return commentRepository.findOne(id);
    }

    @Override
    @Transactional
    public void deleteComment(int id) {
        commentRepository.delete(id);
    }

    @Override
    public List<Comment> getAllByTask_Id(int id) {
        return commentRepository.findAllByTask_Id(id);
    }
}