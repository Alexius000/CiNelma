module cinelma {
	opens main;
	requires javafx.graphics;
	requires javafx.controls;
	requires java.sql;
	requires jfxtras.labs;
	requires javafx.base;
	opens model;
}