package iot.lviv.ua.Repository;

import iot.lviv.ua.domain.Passage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassageRepository extends JpaRepository<Passage, Long> {
}
