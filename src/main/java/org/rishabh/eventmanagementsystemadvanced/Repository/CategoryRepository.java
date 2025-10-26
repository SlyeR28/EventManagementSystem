package org.rishabh.eventmanagementsystemadvanced.Repository;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
