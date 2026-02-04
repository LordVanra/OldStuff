using UnityEngine;
using System.Collections.Generic;

public class Congress : MonoBehaviour
{
    public GameObject congressman;
    private List<Congressman> congress = new List<Congressman>();
    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {
        int r = 20;
        float theta = 0f;
        int perRow = 40;
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < perRow; j++)
            {
                congress.Add(new Congressman(Instantiate(congressman, new Vector2(r * Mathf.Cos(Mathf.Deg2Rad * theta), r * Mathf.Sin(Mathf.Deg2Rad * theta)), Quaternion.identity), theta));
                theta += 180f / (perRow - 1f);
            }
            theta = 0f;
            r += 2;
            perRow += 2;
        }

        int n = 0;
        int red = UnityEngine.Random.Range(115, 271);
        Color redCol = new Color(1f, 0f, 0f, 1f);
        int threshold = 0;
        while (n < red)
        {
            foreach (Congressman man in congress)
            {
                if (man.theta <= threshold && n < red && man.obj.GetComponent<SpriteRenderer>().color == new Color(1f, 1f, 1f, 1f))
                {
                    man.obj.GetComponent<SpriteRenderer>().color = redCol;
                    n += 1;
                }
            }
            threshold += 3;
        }

        n = 0;
        int green = UnityEngine.Random.Range(115, Mathf.Max(271, 500 - red));
        Color greenCol = new Color(0f, 1f, 0f, 1f);
        while (n < green)
        {
            foreach (Congressman man in congress)
            {
                if (man.theta <= threshold && n < green && man.obj.GetComponent<SpriteRenderer>().color == new Color(1f, 1f, 1f, 1f))
                {
                    man.obj.GetComponent<SpriteRenderer>().color = greenCol;
                    n += 1;
                }
            }
            threshold += 3;
        }
        
        foreach (Congressman man in congress)
        {
            if (man.obj.GetComponent<SpriteRenderer>().color == new Color(1f, 1f, 1f, 1f))
            {
                man.obj.GetComponent<SpriteRenderer>().color = new Color(0f, 0f, 1f, 1f);
            }
        }
         
         
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
