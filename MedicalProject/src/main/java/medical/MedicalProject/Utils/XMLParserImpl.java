package medical.MedicalProject.Utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class XMLParserImpl implements XMLParser {
    @Override

    public <O> void exportToXML(O object, String path) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(Object.class);
        Marshaller jaxbMarshaller = context.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        OutputStream outputStream = new FileOutputStream(path);
        BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(outputStream));
        jaxbMarshaller.marshal(object, bfw);

    }

    @Override
    @SuppressWarnings("unchecked")
    public <O> O importFromXML(Class<O> klass, String path) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(klass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (O) unmarshaller.unmarshal(new FileReader(path));
    }
}
