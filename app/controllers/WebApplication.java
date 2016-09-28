package controllers;

import play.*;
import play.mvc.*;
import siena.Model;
import utils.Enums.LogType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

import models.*;

public class WebApplication extends Controller {

    public static void index() {
        render();
    }


}