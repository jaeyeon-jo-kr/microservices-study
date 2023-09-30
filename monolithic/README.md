This is simple monolithic application example.

```shell
npx shadow-cljs -d cider/cider-nrepl:0.28.5 watch :app
```
Download postgres db

```shell
docker run --name m-study-db -e POSTGRES_PASSWORD=micro -p 5432:5432 -d postgres
```