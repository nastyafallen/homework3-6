package ru.hogwarts.school.homework35.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.homework35.model.Avatar;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
