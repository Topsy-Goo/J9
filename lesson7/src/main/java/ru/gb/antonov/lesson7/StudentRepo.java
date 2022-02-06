package ru.gb.antonov.lesson7;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Scope;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository //("student_repo")
//@Scope ("singleton")
public class StudentRepo implements CrudRepository<Student, Long> {
    @PersistenceContext
    private EntityManager entityManager;
//JpaRepositoriesAutoConfiguration p;

    @Override
    public List<Student> findAll () {
        return entityManager.createQuery ("from Student", Student.class).getResultList();

    }

    /**
     Returns all instances of the type {@code T} with the given IDs.
     <p>
     If some or all ids are not found, no entities are returned for these IDs.
     <p>
     Note that the order of elements in the result is not guaranteed.

     @param longs must not be {@literal null} nor contain any {@literal null} values.

     @return guaranteed to be not {@literal null}. The size can be equal or less than the number of given
     {@literal ids}.

     @throws IllegalArgumentException in case the given {@link Iterable ids} or one of its items is {@literal null}.
     */
    @Override
    public Iterable<Student> findAllById (Iterable<Long> longs) {
        return null;
    }

    /**
     Returns the number of entities available.

     @return the number of entities.
     */
    @Override
    public long count () {
        return 0;
    }

    /**
     Deletes the entity with the given id.

     @param aLong must not be {@literal null}.

     @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    @Override
    public void deleteById (Long aLong) {

    }

/*    @Override
    public Object save (Student student) {
        entityManager.persist (student);
        return null;
    }*/


    /**
     Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     entity instance completely.

     @param entity must not be {@literal null}.

     @return the saved entity; will never be {@literal null}.

     @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    @Override
    public <S extends Student> S save (S entity) {
        return null;
    }

    /**
     Saves all given entities.

     @param entities must not be {@literal null} nor must it contain {@literal null}.

     @return the saved entities; will never be {@literal null}. The returned {@literal Iterable} will have the same size
     as the {@literal Iterable} passed as an argument.

     @throws IllegalArgumentException in case the given {@link Iterable entities} or one of its entities is
     {@literal null}.
     */
    @Override
    public <S extends Student> Iterable<S> saveAll (Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Student> findById (Long id) {
        return Optional.of(entityManager.find (Student.class, id));
    }

    /**
     Returns whether an entity with the given id exists.

     @param aLong must not be {@literal null}.

     @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.

     @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    @Override
    public boolean existsById (Long aLong) {
        return false;
    }

    public void update (Student student) {
        entityManager.merge (student);
    }

    @Override
    public void delete (Student student) {
        entityManager.remove (student);
    }

    /**
     Deletes all instances of the type {@code T} with the given IDs.

     @param longs must not be {@literal null}. Must not contain {@literal null} elements.

     @throws IllegalArgumentException in case the given {@literal ids} or one of its elements is {@literal null}.
     @since 2.5
     */
    @Override
    public void deleteAllById (Iterable<? extends Long> longs) {

    }

    /**
     Deletes the given entities.

     @param entities must not be {@literal null}. Must not contain {@literal null} elements.

     @throws IllegalArgumentException in case the given {@literal entities} or one of its entities is {@literal null}.
     */
    @Override
    public void deleteAll (Iterable<? extends Student> entities) {

    }

    /**
     Deletes all entities managed by the repository.
     */
    @Override
    public void deleteAll () {

    }


}
