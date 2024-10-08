# riag/supply/article-mdm/tgm-x2fresh/backend

## Application Architecture Blueprint

Please refer to the Book of Standards and the Application Blueprint Inventory Service for guidance

* [Application Blueprint Inventory Service](https://gitlab.common.cloud.riag.digital/riag/tech/arch/app-arch/blueprint/inv-svc)
* [Book of Standards](https://confluence.rewe-group.at/x/N36sFQ)


## Application Platform Resources

This repository is integrated into the Application Platform. It comes with a preconfigured CI/CD which builds your application, runs tests, pushes a docker image to the container-registry and automatically deploys on our Kubernetes cluster.

Here are some useful links to resources we have already setup for you!

[**Rewe Application Portal (RAP)**](https://rap.common.cloud.riag.digital/clusters/supply/products/article-mdm/applications/tgm-x2fresh/containers/backend) \*

[**Testing Dashboard (SonarQube)**](https://sonarqube.common.cloud.riag.digital/dashboard?id=at.rewe.riag.supply.article-mdm.tgm-x2fresh%3Abackend) \*

**Deployment Spaces (ArgoCD)**

- [Development](https://argocd.development.cloud.riag.digital/applications/riag-supply-article-mdm-tgm-x2fresh?resource=&node=apps%2FDeployment%2Friag-supply-article-mdm-tgm-x2fresh%2Fbackend)
- [Staging](https://argocd.staging.cloud.riag.digital/applications/riag-supply-article-mdm-tgm-x2fresh?resource=&node=apps%2FDeployment%2Friag-supply-article-mdm-tgm-x2fresh%2Fbackend)
- [Production](https://argocd.production.cloud.riag.digital/applications/riag-supply-article-mdm-tgm-x2fresh?resource=&node=apps%2FDeployment%2Friag-supply-article-mdm-tgm-x2fresh%2Fbackend)

**Service URLs**

- [Development](https://platform.development.cloud.riag.digital/riag/supply/article-mdm/tgm-x2fresh/backend/) \*
- [Staging](https://platform.staging.cloud.riag.digital/riag/supply/article-mdm/tgm-x2fresh/backend/) +
- [Production](https://platform.production.cloud.riag.digital/riag/supply/article-mdm/tgm-x2fresh/backend/) +

\* _After the first CI/CD pipeline runs through (couple minutes after repo creation)._  
\+ _Run your products create_container [workflow](https://stackstorm.common.cloud.riag.digital/#/actions/riag_supply_article_mdm.create_container) with the respective stage and start the manual (promote, deploy) CI/CD jobs afterwards._

To learn more about how to use these and other services (i.e. monitoring/alerting and load-testing), feel free to check out our [Confluence Guides](https://confluence.rewe-group.at/display/DP/Application+Platform) to get started!