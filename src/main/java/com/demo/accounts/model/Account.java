package com.demo.accounts.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
* Account
*/
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-09-12T14:12:51.963959300+05:30[Asia/Calcutta]")

    @javax.persistence.Entity
public class Account  implements Serializable {
    private static final long serialVersionUID = 1L;

        @JsonProperty("accountId")
        @javax.persistence.Id
        @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
            private Integer accountId;

        @JsonProperty("accountName")
            private String accountName;

        @JsonProperty("accountHolderName")
            private String accountHolderName;

        @JsonProperty("ifsc")
            private String ifsc;

        @JsonProperty("currency")
            private String currency;

        @JsonProperty("country")
            private String country;

        @JsonProperty("balance")
            private BigDecimal balance;

    public Account accountId(Integer accountId) {
        this.accountId = accountId;
    return this;
    }

    /**
        * Get accountId
    * @return accountId
    */
   // @ApiModelProperty(value = "")
    

  public Integer getAccountId() {
    return accountId;
    }

    public void setAccountId(Integer accountId) {
    this.accountId = accountId;
    }

    public Account accountName(String accountName) {
        this.accountName = accountName;
    return this;
    }

    /**
        * Get accountName
    * @return accountName
    */
    //@ApiModelProperty(value = "")
    

  public String getAccountName() {
    return accountName;
    }

    public void setAccountName(String accountName) {
    this.accountName = accountName;
    }

    public Account accountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    return this;
    }

    /**
        * Get accountHolderName
    * @return accountHolderName
    */
   // @ApiModelProperty(value = "")
    

  public String getAccountHolderName() {
    return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
    this.accountHolderName = accountHolderName;
    }

    public Account ifsc(String ifsc) {
        this.ifsc = ifsc;
    return this;
    }

    /**
        * Get ifsc
    * @return ifsc
    */
   // @ApiModelProperty(value = "")
    

  public String getIfsc() {
    return ifsc;
    }

    public void setIfsc(String ifsc) {
    this.ifsc = ifsc;
    }

    public Account currency(String currency) {
        this.currency = currency;
    return this;
    }

    /**
        * Get currency
    * @return currency
    */
   // @ApiModelProperty(value = "")
    

  public String getCurrency() {
    return currency;
    }

    public void setCurrency(String currency) {
    this.currency = currency;
    }

    public Account country(String country) {
        this.country = country;
    return this;
    }

    /**
        * Get country
    * @return country
    */
   // @ApiModelProperty(value = "")
    

  public String getCountry() {
    return country;
    }

    public void setCountry(String country) {
    this.country = country;
    }

    public Account balance(BigDecimal balance) {
        this.balance = balance;
    return this;
    }

    /**
        * Get balance
    * @return balance
    */
   // @ApiModelProperty(value = "")
    
  @Valid

  public BigDecimal getBalance() {
    return balance;
    }

    public void setBalance(BigDecimal balance) {
    this.balance = balance;
    }


@Override
public boolean equals(Object o) {
if (this == o) {
return true;
}
if (o == null || getClass() != o.getClass()) {
return false;
}
    Account account = (Account) o;
    return Objects.equals(this.accountId, account.accountId) &&
    Objects.equals(this.accountName, account.accountName) &&
    Objects.equals(this.accountHolderName, account.accountHolderName) &&
    Objects.equals(this.ifsc, account.ifsc) &&
    Objects.equals(this.currency, account.currency) &&
    Objects.equals(this.country, account.country) &&
    Objects.equals(this.balance, account.balance);
}

@Override
public int hashCode() {
return Objects.hash(accountId, accountName, accountHolderName, ifsc, currency, country, balance);
}

@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append("class Account {\n");

sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
sb.append("    accountName: ").append(toIndentedString(accountName)).append("\n");
sb.append("    accountHolderName: ").append(toIndentedString(accountHolderName)).append("\n");
sb.append("    ifsc: ").append(toIndentedString(ifsc)).append("\n");
sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
sb.append("    country: ").append(toIndentedString(country)).append("\n");
sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
sb.append("}");
return sb.toString();
}

/**
* Convert the given object to string with each line indented by 4 spaces
* (except the first line).
*/
private String toIndentedString(Object o) {
if (o == null) {
return "null";
}
return o.toString().replace("\n", "\n    ");
}
}
