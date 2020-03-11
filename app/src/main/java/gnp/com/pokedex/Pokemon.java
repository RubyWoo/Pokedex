package gnp.com.pokedex;


public class Pokemon {  //Represento mi modelo de datos, Cliente

    private int number ;
    private String name;
    private String url ;


    //GETTER
    public String getName() {
        return name;
    }
    //SETTER
    public void setName(String name) {
        this.name = name;
    }


    //GETTER
    public int getNumber() {
        String [] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }
    //SETTER
    public void setNumber(int number) {
        this.number = number;
    }


    //GETTER
    public String getUrl() {
        return url;
    }
    //SETTER
    public void setUrl(String url) {
        this.url = url;
    }


}// END CLASS
