per poter lavorare senza root ho docuto forzare l'utente non root per far lavorare maven altrimenti la target era root

in jvm-config ci sono i parametri per far parire il debug, in questo modo eclipse(o comunque il filesystem) riesce a riscrivere nel container

run the project: ./runDocker.sh



# Portainer compose deployment with admin password preset

This file aims to explain how to deploy Portainer inside a compose file with the admin password already set.

## Generate the admin password

For this example, we'll use the password `superpassword`.

Use the following command to generate a hash for the password:

```
docker run --rm httpd:2.4-alpine htpasswd -nbB admin 'superpassword' | cut -d ":" -f 2
```

The output of that command is the hashed password, it should be something similar to `$2y$05$w5wsvlEDXxPjh2GGfkoe9.At0zj8r7DeafAkXXeubs0JnmxLjyw/a`.

## Define the password in the compose file

If you try to use the hashed password in this form directly in your Compose file, the following error will be raised:

```
ERROR: Invalid interpolation format for "command" option in service "portainer": "--admin-password '$2y$05$ZBq/6oanDzs3iwkhQCxF2uKoJsGXA0SI4jdu1PkFrnsKfpCH5Ae4G'"
```

You need to escape each `$` character inside the hashed password with another `$`:

```
$$2y$$05$$ZBq/6oanDzs3iwkhQCxF2uKoJsGXA0SI4jdu1PkFrnsKfpCH5Ae4G
```

Example of valid Compose file:

```yml
version: '2'

services:
  portainer:
    image: portainer/portainer:latest
    ports:
      - "9000:9000"
    command: --admin-password '$$2y$$05$$ZBq/6oanDzs3iwkhQCxF2uKoJsGXA0SI4jdu1PkFrnsKfpCH5Ae4G'
    networks:
      - local
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - portainer-data:/data

networks:
  local:
    driver: bridge

volumes:
  portainer-data:

```


