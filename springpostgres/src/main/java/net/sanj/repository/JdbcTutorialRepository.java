package net.sanj.repository;

import net.sanj.model.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTutorialRepository  implements  TutorialRepository{

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public int save(Tutorial tutorial) {
        return jdbcTemplate.update("INSERT INTO testsc.tutorials (title, description, published) VALUES (?,?,?)",
                new Object[] {tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), tutorial.getId()});
    }

    @Override
    public int update(Tutorial tutorial) {
        return jdbcTemplate.update("UPDATE testsc.tutorials SET title=?, description=?, published=?)  WHERE id=?",
                new Object[] {tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), tutorial.getId()});
    }

    @Override
    public Tutorial findById(Long id) {
        try {
            Tutorial tutorial=jdbcTemplate.queryForObject("Select * from testsc.tutorials where id=?",
                    BeanPropertyRowMapper.newInstance(Tutorial.class),id);
            return tutorial;
        }catch (IncorrectResultSizeDataAccessException e){
            return null;
        }
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM testsc.tutorials WHERE id=?", id);
    }

    @Override
    public List<Tutorial> findAll() {
        return jdbcTemplate.query("SELECT * FROM testsc.tutorials",BeanPropertyRowMapper.newInstance(Tutorial.class));
    }

    @Override
    public List<Tutorial> finddByPulished(boolean published) {
        return jdbcTemplate.query("SELECT * FROM testsc.tutorials WHERE published=?",
                BeanPropertyRowMapper.newInstance(Tutorial.class),published);
    }

    @Override
    public List<Tutorial> findByTitleContaining(String title) {
        String q="SELECT * FOM testsc.tutorials WHERE title ILIKE '%'"+title+"'%";
        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Tutorial.class));
    }

    @Override
    public int deleteAllAll() {
        return jdbcTemplate.update("DELETE FROM testsc.tutorials");
    }
}
