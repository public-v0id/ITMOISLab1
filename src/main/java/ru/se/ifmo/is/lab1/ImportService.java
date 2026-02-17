package ru.se.ifmo.is.lab1;

import ru.se.ifmo.is.lab1.beans.*;
import ru.se.ifmo.is.lab1.dto.importxml.*;
import ru.se.ifmo.is.lab1.repository.ImportRepository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

@Stateless
public class ImportService {

    @PersistenceContext(unitName = "moviesPU")
    private EntityManager em;

    @Inject
    private ImportRepository importRepository;

    private void validatePerson(PersonDto p) {

        if (p.getName() == null || p.getName().isBlank())
            throw new IllegalArgumentException("person name empty");

        if (p.getHairColor() == null)
            throw new IllegalArgumentException("hairColor required");

        if (p.getPassportID() == null || p.getPassportID().isBlank())
            throw new IllegalArgumentException("passportID empty");
    }

    private void validate(MovieImportDto dto) {

        if (dto.getName() == null || dto.getName().isBlank())
            throw new IllegalArgumentException("name empty");

        if (dto.getOscarsCount() <= 0)
            throw new IllegalArgumentException("oscarsCount must be > 0");

        if (dto.getBudget() == null || dto.getBudget() <= 0)
            throw new IllegalArgumentException("budget must be > 0");

        if (dto.getTotalBoxOffice() == null || dto.getTotalBoxOffice() <= 0)
            throw new IllegalArgumentException("totalBoxOffice must be > 0");

        if (dto.getLength() == null || dto.getLength() <= 0)
            throw new IllegalArgumentException("length must be > 0");

        if (dto.getGoldenPalmCount() <= 0)
            throw new IllegalArgumentException("goldenPalmCount must be > 0");

        if (dto.getUsaBoxOffice() <= 0)
            throw new IllegalArgumentException("usaBoxOffice must be > 0");

        if (dto.getTagline() == null)
            throw new IllegalArgumentException("tagline required");

        CoordinatesDto c = dto.getCoordinates();

        if (c.getX() == null || c.getX() > 812)
            throw new IllegalArgumentException("coordinates.x invalid");

        if (c.getY() == null || c.getY() > 944)
            throw new IllegalArgumentException("coordinates.y invalid");

        validatePerson(dto.getDirector());
        validatePerson(dto.getOperator());

        if (dto.getScreenwriter() != null)
            validatePerson(dto.getScreenwriter());
    }

    private Person mapPerson(PersonDto dto) {

        if (dto == null) {
            return null;
        }

        Person person = new Person();

        person.setName(dto.getName());
        person.setEyeColor(dto.getEyeColor());
        person.setHairColor(dto.getHairColor());
        person.setPassportID(dto.getPassportID());
        person.setNationality(dto.getNationality());

        if (dto.getLocation() != null) {

            LocationDto locDto = dto.getLocation();

            Location location = new Location();
            location.setX(locDto.getX());
            location.setY(locDto.getY());
            location.setZ(locDto.getZ());

            person.setLocation(location);
        }

        return person;
    }

    private Movie map(MovieImportDto dto) {

        Coordinates coords = new Coordinates();
        coords.setX(dto.getCoordinates().getX());
        coords.setY(dto.getCoordinates().getY());

        Person director = mapPerson(dto.getDirector());
        Person operator = mapPerson(dto.getOperator());

        Person screenwriter = null;
        if (dto.getScreenwriter() != null)
            screenwriter = mapPerson(dto.getScreenwriter());

        Movie movie = new Movie();
        movie.setName(dto.getName());
        movie.setCoordinates(coords);
        movie.setCreationDate(LocalDateTime.now());
        movie.setOscarsCount(dto.getOscarsCount());
        movie.setBudget(dto.getBudget());
        movie.setTotalBoxOffice(dto.getTotalBoxOffice());
        movie.setMpaaRating(dto.getMpaaRating());
        movie.setDirector(director);
        movie.setOperator(operator);
        movie.setScreenwriter(screenwriter);
        movie.setLength(dto.getLength());
        movie.setGoldenPalmCount(dto.getGoldenPalmCount());
        movie.setUsaBoxOffice(dto.getUsaBoxOffice());
        movie.setTagline(dto.getTagline());
        movie.setGenre(dto.getGenre());

        return movie;
    }

    private Person preparePerson(Person person) {
        // Check if a person with this passport ID already exists
        if (person.getPassportID() != null) {
            Person existing = em.createQuery(
                            "SELECT p FROM person p WHERE p.passportID = :pid", Person.class)
                    .setParameter("pid", person.getPassportID())
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            if (existing != null) {
                return existing; // Return the managed entity
            }
        }

        // Handle location (if any)
        Location location = person.getLocation();
        if (location != null) {
            if (location.getId() == null) {
                em.persist(location);
            } else {
                location = em.merge(location);
            }
            person.setLocation(location);
        }

        // Persist the new person
        em.persist(person);
        return person;
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void importXml(InputStream file, String username) throws Exception {

        ImportOperation op = new ImportOperation();
        op.setUsername(username);
        op.setStatus(ImportStatus.IN_PROGRESS);
        op.setStartedAt(LocalDateTime.now());
        importRepository.save(op);

        try {

            JAXBContext context = JAXBContext.newInstance(MovieImportList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            MovieImportList list = (MovieImportList) unmarshaller.unmarshal(file);

            int count = 0;

            for (MovieImportDto dto : list.getMovies()) {

                validate(dto);

                // Маппим DTO в сущности
                Movie movie = map(dto);

                // Сначала сохраняем связанные сущности
                Person director = movie.getDirector();
                if (director != null) {
                    director = preparePerson(director);
                    movie.setDirector(director);
                }

                Person operator = movie.getOperator();
                if (operator != null) {
                    operator = preparePerson(operator);
                    movie.setOperator(operator);
                }

                Person screenwriter = movie.getScreenwriter();
                if (screenwriter != null) {
                    screenwriter = preparePerson(screenwriter);
                    movie.setScreenwriter(screenwriter);
                }
                Coordinates coords = movie.getCoordinates();
                if (coords != null) {
                    if (coords.getId() == null) {
                        em.persist(coords); // сначала сохраняем координаты
                    } else {
                        coords = em.merge(coords); // если уже существует
                    }
                    movie.setCoordinates(coords); // на всякий случай, чтобы использовать управляемый экземпляр
                }

                // Теперь можно сохранять фильм
                System.out.println("Persisting movie: " + movie.getName() + ", tagline length: " + movie.getTagline().length());
                em.persist(movie);

                count++;
            }

            op.setStatus(ImportStatus.SUCCESS);
            op.setCreatedCount(count);
            op.setFinishedAt(LocalDateTime.now());

        } catch (Exception e) {
            op.setStatus(ImportStatus.FAILED);
            op.setFinishedAt(LocalDateTime.now());
            throw e;
        }
    }
}
