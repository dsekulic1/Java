package ba.unsa.etf.rpr;

public class Odgovor {
    private String tekstOdgovora;
    private boolean tacno;

    public Odgovor(String tekstOdgovora, boolean tacno) {
        this.tekstOdgovora = tekstOdgovora;
        this.tacno = tacno;
    }

    public String getTekstOdgovora() {
        return tekstOdgovora;
    }

    public void setTekstOdgovora(String tekstOdgovora) {
        this.tekstOdgovora = tekstOdgovora;
    }

    public boolean isTacno() {
        return tacno;
    }

    public void setTacno(boolean tacno) {
        this.tacno = tacno;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Odgovor odgovor = (Odgovor) obj;
        return (odgovor.isTacno() == tacno && odgovor.getTekstOdgovora().equals(tekstOdgovora));
    }
}
