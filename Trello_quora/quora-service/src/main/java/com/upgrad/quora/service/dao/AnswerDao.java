package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDao {
    @PersistenceContext
    private EntityManager entityManager;

    public AnswerEntity createAnswer ( AnswerEntity answerEntity)
    {
        entityManager.persist(answerEntity);
        return answerEntity;
    }

    public List<AnswerEntity> getAllAnswersByQuestion(final QuestionEntity questionId) {
        return entityManager
                .createNamedQuery("getAnswerByQuestion", AnswerEntity.class)
                .setParameter("questionId", questionId)
                .getResultList();
    }
}
