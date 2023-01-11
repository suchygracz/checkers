package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;




/**
 * Klasa MyCircle rozszerzająca klasę Ellipse
 */
class MyCircle extends Ellipse {
    /**
     *
     * @param xSrodka położenie x'owe środka koła
     * @param ySrodka położenie y'owe środka koła
     * @param promienWOX promień koła
     * @param promienWOY promień koła
     * MyCircle konstruktor klasy dodający odpowiednie handlery do koła
     * setOnMouseEntered/Exited odpowiada za podświetlenie i wysuniecie na góre koła w przypadku najechaniu na niego kursorem
     */
    MyCircle(double xSrodka, double ySrodka, double promienWOX, double promienWOY){
        super(xSrodka, ySrodka, promienWOX, promienWOY);
        setOnScroll(new MyCircleScrollHandler());
        setOnMouseDragged(new MyCircleEventHandler());
        setOnMouseClicked(new MyCircleEventHandler());

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle("-fx-stroke: limeGreen; -fx-stroke-width: 4;");
                toFront();
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle(null);
            }
        });
    }
    /**
     * isHit funkcja sprawdzająca czy naciśnięty punkt znajduje się w obrębie figury
     * @param x współrzędna x'owa
     * @param y współrzędna y'owa
     * @return zwaraca wartość bolean
     */
    public boolean isHit(double x, double y){
        return getBoundsInLocal().contains(x, y);
    }
    /**
     * addX funkcja odpowiada za przesuwanie figury w osi x'ów
     * @param x wartość o jaką powinna być przesunięta figura w osi x'ów
     */
    public void addX(double x){
        setCenterX(getCenterX() + x);
    }
    /**
     * addY funkcja odpowiada za przesuwanie figury w osi y'ów
     * @param y wartość o jaką powinna być przesunięta figura w osi y'ów
     */
    public void addY(double y){
        setCenterY(getCenterY() + y);
    }
    /**
     * MyCircleEventHandler klasa rozszerzająca EventHandler<MouseEvent> odpowiadająca za możliwość przesuwania i obracania figury a także zmianę jaj koloru
     */
    class MyCircleEventHandler implements EventHandler<MouseEvent>{
        MyCircle myCircle;
        private double x;
        private double y;

        /**
         * moveCircle funkcja odpowiadająca za przemieszczanie figury
         */
        public void moveCircle(MouseEvent mouseEvent){
            double dx = mouseEvent.getX() - x;
            double dy = mouseEvent.getY() - y;

            if(myCircle.isHit(x, y)){
                myCircle.addX(dx);
                myCircle.addY(dy);
            }
            x += dx;
            y += dy;
        }

        public void handle(MouseEvent mouseEvent){
            myCircle = (MyCircle) mouseEvent.getSource();
            if(mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton() == MouseButton.SECONDARY){
                double xRotationCenter = myCircle.getCenterX();
                double yRotationCenter = myCircle.getCenterY();
                GridPane gridPane = new GridPane();
                Label colorPlabel = new Label("Wybierz kolor figury: ");
                colorPlabel.setMinHeight(20);
                colorPlabel.setLayoutX(0);
                Label rotateLabel = new Label("wybierz o jaki stopień chcesz obrócić figure");
                ColorPicker colorPicker = new ColorPicker();
                Slider slider = new Slider(0, 360, 0);
                slider.setShowTickLabels(true);
                slider.setShowTickMarks(true);
                slider.setMajorTickUnit(90);
                slider.setBlockIncrement(1);
                slider.setLayoutX(40);
                slider.setLayoutY(195);
                Rotate rotate = new Rotate();
                rotate.setPivotX(xRotationCenter);
                rotate.setPivotY(yRotationCenter);
                /**
                 * Listener pozwalający zczytać wartość z slidera
                 */
                slider.valueProperty().addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue<?extends Number> observable, Number oldValue, Number newValue){
                        //Setting the angle for the rotation
                        rotate.setAngle((double) newValue);
                        //myRectangle.getTransforms().add(rotate);
                    }
                });
                myCircle.getTransforms().add(rotate);
                /**
                 * Listener pozwalający zczytać wartość koloru z colorPickera
                 */
                colorPicker.valueProperty().addListener(new ChangeListener<Color>() {
                    @Override
                    public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1) {
                        Color myColor = colorPicker.getValue();
                        myCircle.setFill(myColor);
                    }
                });
                gridPane.add(colorPlabel,0,0);
                gridPane.add(colorPicker,0,1);
                gridPane.add(rotateLabel,1,0);
                gridPane.add(slider,1,1);
                gridPane.setHgap(10);
                gridPane.setVgap(10);

                Scene prScene = new Scene(gridPane,420,69);
                Stage prStage = new Stage();
                prStage.setTitle("wybór koloru i rotowanie figury");
                prStage.setScene(prScene);
                prStage.show();
            }
            if(mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED){
                moveCircle(mouseEvent);
            }

        }

    }
    class MyCircleScrollHandler implements EventHandler<ScrollEvent>{
        MyCircle myCircle;
        /**
         * ScaleCircle funkcja odpowiedzialna za Skalowanie figury
         */
        public void ScaleCircle(ScrollEvent scrollEvent){
            double x = scrollEvent.getX();
            double y = scrollEvent.getY();
            if (myCircle.isHit(x, y)){
                myCircle.setScaleX(myCircle.getScaleX()+scrollEvent.getTextDeltaY()*0.01);
                myCircle.setScaleY(myCircle.getScaleY()+scrollEvent.getTextDeltaY()*0.01);
            }

        }
        public void handle(ScrollEvent scrollEvent){
            myCircle = (MyCircle) scrollEvent.getSource();
            if(scrollEvent.getEventType()== ScrollEvent.SCROLL){
                ScaleCircle(scrollEvent);
            }
        }

    }
}

