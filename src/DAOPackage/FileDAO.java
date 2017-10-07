package DAOPackage;

public class FileDAO {
    private String fileLocation;

    public FileDAO(){
        this.fileLocation = "";
    }

    public FileDAO(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFilelocation() {
        return fileLocation;
    }

    public void setFilelocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
