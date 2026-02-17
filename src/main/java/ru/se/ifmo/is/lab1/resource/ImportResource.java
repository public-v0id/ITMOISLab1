package ru.se.ifmo.is.lab1.resource;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
import ru.se.ifmo.is.lab1.ImportService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

@Path("/import")
@Consumes("multipart/form-data")
public class ImportResource {

    @Inject
    private ImportService importService;

    @POST
    @Consumes("multipart/form-data")
    public Response uploadFile(MultipartFormDataInput input) {
        try {
            // Получаем файл
            InputPart filePart = input.getFormDataMap().get("file").get(0);
            InputStream fileStream = filePart.getBody(InputStream.class, null);

            // Получаем username
            InputPart usernamePart = input.getFormDataMap().get("username").get(0);
            String username = usernamePart.getBodyAsString();

            System.out.println("username = " + username);

            importService.importXml(fileStream, username);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}