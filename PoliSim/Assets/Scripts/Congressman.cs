using UnityEngine;

public class Congressman
{
    public GameObject obj; 
    public float theta;

    public Congressman(GameObject obj, float angle)
    {
        this.obj = obj;
        theta = angle;
    }
}