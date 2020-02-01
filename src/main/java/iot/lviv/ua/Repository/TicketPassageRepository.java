package iot.lviv.ua.Repository;

import iot.lviv.ua.domain.TicketPassage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPassageRepository extends JpaRepository<TicketPassage, Long> {
}
