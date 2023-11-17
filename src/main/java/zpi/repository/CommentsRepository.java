package zpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zpi.entity.Comment;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Integer> {
    List<Comment> getAllByAttractionId(Integer attractionId);
}
