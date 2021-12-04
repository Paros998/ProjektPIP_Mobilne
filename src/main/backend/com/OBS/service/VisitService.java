package com.OBS.service;

import com.OBS.entity.Visit;
import com.OBS.repository.VisitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final EmployeeService employeeService;

    private String doesntExist(Long id) { return  "Visit by given id:" + id + " doesn't exists in database" ;}

    public Visit getVisit(Long id) {
        return visitRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(doesntExist(id))
        );
    }

    public void addVisit(Visit visit) {
        visitRepository.save(visit);
    }

    public List<Visit> getVisits() {
        return visitRepository.findAll();
    }

    @Transactional
    public void setInactive(Long id) {
        Visit visit = visitRepository.findById(id).orElseThrow(
                () ->  new IllegalStateException(doesntExist(id))
        );

        visit.setIsActive(false);
        visitRepository.save(visit);
    }


    public void deleteVisit(Long id) {
        if(!visitRepository.existsById(id))
            throw new IllegalStateException(doesntExist(id));
        visitRepository.deleteById(id);
    }

    public List<Visit> getEmployeeVisits(Long id) {
        return visitRepository.findAllByEmployee_EmployeeId(id);
    }

    public List<Visit> getVisitsUnassigned() {
        return visitRepository.findAllByEmployeeNull();
    }

    @Transactional
    public void setEmployee(Long id, Long employeeID) {
        Visit visit = visitRepository.findById(id).orElseThrow(
                () ->  new IllegalStateException(doesntExist(id))
        );

        visit.setEmployee(employeeService.getEmployee(employeeID));

        visitRepository.save(visit);
    }
}
