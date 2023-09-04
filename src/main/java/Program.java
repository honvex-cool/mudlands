class Program {
    public static void main(String[] args) {
        GdxProgram gdxProgram = new GdxProgram();
        MudlandsGame mudlandsGame = new MudlandsGame();
        gdxProgram.run(mudlandsGame);
        mudlandsGame.dispose();
    }
}
