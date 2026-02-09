package ru.se.ifmo.is.lab1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class MyApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ru.se.ifmo.is.lab1.filters.CORSFilter.class);
        classes.add(ru.se.ifmo.is.lab1.resource.MovieResource.class);
        classes.add(ru.se.ifmo.is.lab1.resource.PersonResource.class);
        classes.add(ru.se.ifmo.is.lab1.resource.CoordinatesResource.class);
        classes.add(ru.se.ifmo.is.lab1.resource.LocationResource.class);
        return classes;
    }
}
